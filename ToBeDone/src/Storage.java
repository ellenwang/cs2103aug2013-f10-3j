import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

public class Storage {
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
				out.write(storageFormatOfTask);
			}

			out.close();
		} catch (IOException e) {
			System.err.println("");
		}
	}
	
	
	private static void updateTaskId(Vector<TaskItem> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setTaskID(i);
		}
	}

	public static void store(TaskItem task) {
		tasks.add(task);
		updateTaskId(tasks);
		writeTasksToFile(tasks);
	}

	public static void store(int taskIndex, TaskItem task) {
		tasks.add(taskIndex, task);
		updateTaskId(tasks);
		writeTasksToFile(tasks);
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
	
	public static TaskItem delete(int taskIndex) {
		Vector<TaskItem> finishedTask = new Vector<TaskItem>();
		Date currentDate = (Date) Calendar.getInstance().getTime();
		for (int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getEndTime().after(currentDate)){
				finishedTask.add(tasks.get(i));
			}
		}
		return finishedTask;
	}

	public static Vector<TaskItem> retrieveUnfinished(){
		Vector<TaskItem> finishedTask = new Vector<TaskItem>();
		Date currentDate = (Date) Calendar.getInstance().getTime();
		for (int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getEndTime().before(currentDate)){
				finishedTask.add(tasks.get(i));
			}
		}
		return finishedTask;
	}
	public static TaskItem delete(int taskIndex) {
		TaskItem deletedTask = tasks.remove(taskIndex);
		updateTaskId(tasks);
		writeTasksToFile(tasks);
		return deletedTask;
	}

	private static String taskItemToStorageFormat(TaskItem task) {
		String storageFormat = "\"" + task.getTaskDescription() + "\"";
		return storageFormat;
	}

	private static TaskItem storageFormatToTaskItem(String storageFormat) {
		TaskItem task = new TaskItem();
		String description = storageFormat.substring(1,
				storageFormat.lastIndexOf('\"'));
		task.setTaskDescription(description);
		return task;
	}

	public static void clear() {
		tasks.clear();
		writeTasksToFile(tasks);
	}
}
