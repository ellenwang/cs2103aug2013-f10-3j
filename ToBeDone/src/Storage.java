import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;


public class Storage {
	// messages to the user
	private static final String MESSAGE_FAILED_TO_WRITE_TO_FILE = "Failed to write to file.";
	private static final String MESSAGE_FAILED_TO_READ_FROM_FILE = "Failed to read from file.";
	private static final String MESSAGE_PARSE_DATE_FAILED = "Failed to parse date.";
	private static final String FILE_NAME = "ToBeDone.txt";
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"MM/dd','HH:mm");
	
	// file written to and read from
	private static File file = new File(FILE_NAME);

	
	public static void store(Vector<TaskItem> taskItems) {
		writeTasksToFile(taskItems);
	}
	
	public static Vector<TaskItem> retrieve() {
		Vector<TaskItem> allTasks = getTasksFromFile();
		return allTasks;
	}
	
	private static Vector<TaskItem> getTasksFromFile() {
		Vector<TaskItem> fileTasks = new Vector<TaskItem>();
		try {
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String storageFormatOfTask = in.nextLine();
				TaskItem fileTask = storageFormatToTaskItem(storageFormatOfTask);
				fileTasks.add(fileTask);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(MESSAGE_FAILED_TO_READ_FROM_FILE);
		}
		return fileTasks;
	}

	private static void writeTasksToFile(Vector<TaskItem> tasks) {
		try {
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			for (TaskItem task : tasks) {
				String storageFormatOfTask = taskItemToStorageFormat(task);
				out.write(storageFormatOfTask + "\n");
			}

			out.close();
		} catch (IOException e) {
			System.err.println(MESSAGE_FAILED_TO_WRITE_TO_FILE);
		}
	}

	
	private static String taskItemToStorageFormat(TaskItem task) {
		String description = task.getDescription();

		Date startTime = task.getStartTime();
		String startTimeFormatted;
		if (startTime != null) {
			startTimeFormatted = simpleDateFormat.format(startTime);
		} else {
			startTimeFormatted = "";
		}

		Date endTime = task.getEndTime();
		String endTimeFormatted;
		if (endTime != null) {
			endTimeFormatted = simpleDateFormat.format(endTime);
		} else {
			endTimeFormatted = "";
		}

		int priority = task.getPriority();
		String separator = ";";

		String storageFormat = "\"" + description + "\"" + startTimeFormatted
				+ separator + endTimeFormatted + separator + priority;

		return storageFormat;
	}
	
	private static TaskItem storageFormatToTaskItem(String storageFormat) {
		String description = extractDescription(storageFormat);
		String storageFormatWithoutDescription = removeDescription(storageFormat);
		String[] taskInformation = storageFormatWithoutDescription.split(";");
		Date startTime = null;
		Date endTime = null;
		try {
			if (!taskInformation[0].equals("")) {
				startTime = simpleDateFormat.parse(taskInformation[0]);
			}

			if (!taskInformation[1].equals("")) {
				endTime = simpleDateFormat.parse(taskInformation[1]);
			}
		} catch (Exception e) {
			System.err.println(MESSAGE_PARSE_DATE_FAILED);
		}
		int priority = Integer.parseInt(taskInformation[2]);
		TaskItem task = new TaskItem(description, startTime, endTime, priority);

		return task;
	}
	
	private static String extractDescription(String storageFormat) {
		int indexOfEndOfDescription = storageFormat.lastIndexOf('\"');
		String description = storageFormat
				.substring(1, indexOfEndOfDescription);
		return description;
	}

	private static String removeDescription(String storageFormat) {
		int indexOfEndOfDescription = storageFormat.lastIndexOf('\"');
		String storageFormatWithoutDescription = storageFormat
				.substring(indexOfEndOfDescription + 1);
		return storageFormatWithoutDescription;
	}
}
