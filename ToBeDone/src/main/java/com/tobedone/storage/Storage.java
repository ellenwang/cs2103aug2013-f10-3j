package com.tobedone.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * 
 * @author A0118441M
 * @version 0.5
 * @since 11-11-2013
 * 
 */
public class Storage {
	// logger
	private static Logger logger = Logger.getLogger(Storage.class);

	// file written to and read from
	private File file;

	// singleton storage object
	private static Storage storage;

	/**
	 * Singleton default constructor.
	 * 
	 */
	private Storage() {
		file = new File(Constants.FILE_NAME);
	}

	/**
	 * Gets an instance of the singleton Storage object.
	 * 
	 * @return singleton instance
	 */
	public static Storage getInstance() {
		if (storage == null) {
			storage = new Storage();
		}
		return storage;
	}

	/**
	 * Used for testing. Change the file written to and read from to a test
	 * file.
	 */
	public void changeToTestFile() {
		logger.info(LogMessages.INFO_CHANGE_FILE_TEST);
		file = new File(Constants.TEST_FILE_NAME);
	}

	/**
	 * Used for testing. Change the file written to and read from to the main
	 * file.
	 */
	public void changeToMainFile() {
		logger.info(LogMessages.INFO_CHANGE_FILE_MAIN);
		file = new File(Constants.FILE_NAME);
	}

	/**
	 * Stores the specified tasks in the file.
	 * 
	 * @param taskItems
	 *            the tasks to be stored
	 * @return true if argument is not null and the items has been stored
	 *         successfully; false otherwise
	 */
	public boolean store(Vector<TaskItem> taskItems) {
		if (taskItems != null) {
			return writeTasksToFile(taskItems);
		}
		return false;
	}

	/**
	 * Retrieves all tasks from the file.
	 * 
	 * @return all tasks stored in the file
	 */
	public Vector<TaskItem> retrieve() {
		Vector<TaskItem> fileTasks = new Vector<TaskItem>();
		try {
			logger.info(LogMessages.INFO_ACCESS_FILE_CONTENT);
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String storageFormatOfTask = in.nextLine();
				TaskItem fileTask = storageFormatToTaskItem(storageFormatOfTask);
				fileTasks.add(fileTask);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(Constants.MESSAGE_FAILED_TO_READ_FROM_FILE);
			logger.error(LogMessages.ERROR_ACCESS_FILE_CONTENT);
		}
		return fileTasks;
	}

	/**
	 * Stores the specified tasks in the file.
	 * 
	 * @param tasks
	 *            the tasks to be stored
	 * @return true if the tasks are successfully stored to the file; false
	 *         otherwise
	 */
	private boolean writeTasksToFile(Vector<TaskItem> tasks) {
		try {
			logger.info(LogMessages.INFO_WRITE_TO_FILE);
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			for (TaskItem task : tasks) {
				String storageFormatOfTask = taskItemToStorageFormat(task);
				out.write(storageFormatOfTask + "\n");
			}

			out.close();

			return true;
		} catch (IOException e) {
			System.err.println(Constants.MESSAGE_FAILED_TO_WRITE_TO_FILE);
			logger.error(LogMessages.ERROR_WRITE_TO_FILE);
		}
		return false;
	}

	/**
	 * Converts a task item into the storage format, that is how the task is
	 * stored in the file.
	 * 
	 * @param task
	 *            the task item to be converted into storage format
	 * @return the storage format of the task
	 */

	private String taskItemToStorageFormat(TaskItem task) {
		String description = task.getDescription();

		Date startTime;
		Date endTime;
		String formattedStartTime = null;
		String formattedEndTime = null;

		if (task instanceof FloatingTask) {
			formattedStartTime = null;
			formattedEndTime = null;
		} else if (task instanceof DeadlinedTask) {
			formattedStartTime = null;
			endTime = ((DeadlinedTask) task).getEndTime();
			formattedEndTime = formatDate(endTime);
		} else if (task instanceof TimedTask) {
			startTime = ((TimedTask) task).getStartTime();
			formattedStartTime = formatDate(startTime);
			endTime = ((TimedTask) task).getEndTime();
			formattedEndTime = formatDate(endTime);
		}

		int priority = task.getPriority();
		TaskItem.Status status = task.getStatus();

		String storageFormat = "\"" + description + "\"" + formattedStartTime
				+ ";" + formattedEndTime + ";" + priority + ";" + status;

		return storageFormat;
	}

	/**
	 * Converts a task in storage format, that is how the task is stored in the
	 * file, to a task item.
	 * 
	 * @param storageFormat
	 *            the storage format of the task
	 * @return the storage format converted into a task item
	 */
	private TaskItem storageFormatToTaskItem(String storageFormat) {
		String[] taskInformation = splitStorageFormat(storageFormat);

		assert taskInformation.length == 5;
		String description = taskInformation[0];
		Date startTime = parseDate(taskInformation[1]);
		Date endTime = parseDate(taskInformation[2]);
		int priority = Integer.parseInt(taskInformation[3]);
		TaskItem.Status status = TaskItem.Status.valueOf(taskInformation[4]);

		TaskItem task = null;
		if (startTime == null && endTime == null) {
			task = new FloatingTask(description, priority);
		} else if (startTime == null && endTime != null) {
			task = new DeadlinedTask(description, endTime, priority);
		} else if (startTime != null && endTime != null) {
			task = new TimedTask(description, startTime, endTime, priority);
		}

		task.setStatus(status);
		return task;
	}

	/**
	 * Converts a date into a string representation.
	 * 
	 * @param date
	 *            the date to be converted
	 * @return string representation of date
	 */
	private String formatDate(Date date) {
		String formattedDate;
		if (date != null) {
			formattedDate = Constants.simpleDateFormat.format(date);
		} else {
			formattedDate = "";
		}

		return formattedDate;
	}

	/**
	 * Parses a string representation of a date to a date.
	 * 
	 * @param date
	 *            the string representation of the date
	 * @return the string representation parsed to a date
	 */
	private Date parseDate(String date) {
		Date parsedDate = null;
		if (!date.equals("null")) {
			try {
				parsedDate = Constants.simpleDateFormat.parse(date);
			} catch (ParseException e) {
				System.err.println(Constants.MESSAGE_FAILED_TO_PARSE_DATE);
			}
		}

		return parsedDate;
	}

	/**
	 * Splits a storage format into an array containing the representation of
	 * the different fields of the task.
	 * 
	 * @param storageFormat
	 *            the storage format to be split
	 * @return an array of the string representation of the different fields in
	 *         a task
	 */
	private String[] splitStorageFormat(String storageFormat) {
		String description = extractDescription(storageFormat);
		String storageFormatWithoutDescription = removeDescription(storageFormat);

		String[] taskInformationExceptDescription = storageFormatWithoutDescription
				.split(";");
		String[] allTaskInformation = new String[taskInformationExceptDescription.length + 1];

		allTaskInformation[0] = description;
		for (int i = 0; i < taskInformationExceptDescription.length; i++) {
			allTaskInformation[i + 1] = taskInformationExceptDescription[i];
		}

		return allTaskInformation;
	}

	/**
	 * Extracts the description of a storage format.
	 * 
	 * @param storageFormat
	 *            the storage format from which the description is to be
	 *            extracted
	 * @return the description of the task represented by the storage format
	 */
	private String extractDescription(String storageFormat) {
		int indexOfEndOfDescription = storageFormat.lastIndexOf('\"');
		String description = storageFormat
				.substring(1, indexOfEndOfDescription);
		return description;
	}

	/**
	 * Removes the description from a storage format.
	 * 
	 * @param storageFormat
	 *            the storage format from which the description is to be removed
	 * @return the specified storage format without the description
	 */
	private String removeDescription(String storageFormat) {
		int indexOfEndOfDescription = storageFormat.lastIndexOf('\"');
		String storageFormatWithoutDescription = storageFormat
				.substring(indexOfEndOfDescription + 1);
		return storageFormatWithoutDescription;
	}
}
