import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class Storage {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"MM-dd'at'HH:mm");

	private static String defaultFileName = "ToBeDone.txt";

	private static File file = new File(defaultFileName);
	private static Vector<TaskItem> tasks = getTasksFromFile();

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
			System.err.println("");
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
			System.err.println("");
		}
	}

	private static void updateTaskIDs(Vector<TaskItem> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setTaskID(i);
		}
	}

	public static TaskItem store(TaskItem task) {
		tasks.add(task);
		updateTaskIDs(tasks);
		writeTasksToFile(tasks);
		return task;
	}

	public static TaskItem store(int taskIndex, TaskItem task) {
		tasks.add(taskIndex, task);
		updateTaskIDs(tasks);
		writeTasksToFile(tasks);
		return task;
	}

	public static Vector<TaskItem> search(String keyword) {
		Vector<TaskItem> matchingTasks = new Vector<TaskItem>();

		for (TaskItem task : tasks) {
			String taskInfo = task.toString();
			if (taskInfo.contains(keyword)) {
				matchingTasks.add(task);
			}
		}

		return matchingTasks;
	}

	public static TaskItem retrieve(int taskIndex) {
		TaskItem targetTask = tasks.get(taskIndex);
		return targetTask;
	}

	public static Vector<TaskItem> retrieveAll() {
		return tasks;
	}

	public static Vector<TaskItem> retrieveUnfinished() {
		Vector<TaskItem> finishedTask = new Vector<TaskItem>();

		for (int i = 0; i < tasks.size(); i++) {
			TaskItem current = tasks.get(i);
			current.updateStatus();
			int curstatus = current.getStatus();
			if (curstatus == 1) {
				finishedTask.add(tasks.get(i));
			}
		}
		return finishedTask;
	}

	public static Vector<TaskItem> retrieveFinished() {
		Vector<TaskItem> finishedTask = new Vector<TaskItem>();
		for (int i = 0; i < tasks.size(); i++) {
			TaskItem current = tasks.get(i);
			current.updateStatus();
			int curstatus = current.getStatus();
			if (curstatus == 2) {
				finishedTask.add(tasks.get(i));
			}
		}
		return finishedTask;
	}

	public static Vector<TaskItem> retrieveExpired() {
		Vector<TaskItem> finishedTask = new Vector<TaskItem>();
		Date currentDate = (Date) Calendar.getInstance().getTime();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getEndTime().after(currentDate)) {
				finishedTask.add(tasks.get(i));
			}
		}
		return finishedTask;
	}

	public static TaskItem delete(int taskIndex) {
		TaskItem deletedTask = tasks.remove(taskIndex);
		updateTaskIDs(tasks);
		writeTasksToFile(tasks);
		return deletedTask;
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
			e.printStackTrace();
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

	public static void clear() {
		tasks.clear();
		writeTasksToFile(tasks);
	}
}
