import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class Storage {
	// messages to the user
	private static final String MESSAGE_FAILED_TO_WRITE_TO_FILE = "Failed to write to file.";
	private static final String MESSAGE_FAILED_TO_READ_FROM_FILE = "Failed to read from file.";
	private static final String MESSAGE_FAILED_TO_PARSE_DATE = "Failed to parse date.";
	private static final String FILE_NAME = "ToBeDone.txt";

	// file written to and read from
	private static File file = new File(FILE_NAME);

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM','HH:mm");

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
		String formattedStartTime = formatDate(startTime);

		Date endTime = task.getEndTime();
		String formattedEndTime = formatDate(endTime);

		int priority = task.getPriority();
		TaskItem.Status status = task.getStatus();

		String storageFormat = "\"" + description + "\"" + formattedStartTime
				+ ";" + formattedEndTime + ";" + priority + ";" + status;

		return storageFormat;
	}

	private static String formatDate(Date date) {
		String formattedDate;
		if (date != null) {
			formattedDate = simpleDateFormat.format(date);
		} else {
			formattedDate = "";
		}

		return formattedDate;
	}

	private static TaskItem storageFormatToTaskItem(String storageFormat) {
		String[] taskInformation = splitStorageFormat(storageFormat);

		String description = taskInformation[0];
		Date startTime = parseDate(taskInformation[1]);
		Date endTime = parseDate(taskInformation[2]);
		int priority = Integer.parseInt(taskInformation[3]);
		TaskItem.Status status = TaskItem.Status.valueOf(taskInformation[4]);

		TaskItem task = new TaskItem(description, startTime, endTime, priority,
				status);
		return task;
	}
	
	private static Date parseDate(String date) {
		Date parsedDate = null;
		if (!date.equals("")) {
			try {
				parsedDate = simpleDateFormat.parse(date);
			} catch (ParseException e) {
				System.err.println(MESSAGE_FAILED_TO_PARSE_DATE);
			}
		}
		
		return parsedDate;
	}

	private static String[] splitStorageFormat(String storageFormat) {
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
