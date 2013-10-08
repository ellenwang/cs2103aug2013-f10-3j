import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;
 
public class Storage {
	private static File file;
	private static Vector<TaskItem> tasks;

	public Storage(String fileName) {
		file = new File(fileName);
		tasks = getTasksFromFile();
	}

	private Vector<TaskItem> getTasksFromFile() {
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

	public void store(TaskItem task) {
		tasks.add(task);
		writeTasksToFile(tasks);
	}

	public void store(int taskIndex, TaskItem task) {
		tasks.add(taskIndex, task);
		writeTasksToFile(tasks);
	}

	public static TaskItem retrieve(int taskIndex) {
		TaskItem targetTask = tasks.get(taskIndex);
		return targetTask;
	}

	public static Vector<TaskItem> retrieveAll() {
		return tasks;
	}
	
	public static Vector<TaskItem> retrieveFinished(){
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
		writeTasksToFile(tasks);
		return deletedTask;
	}

	private static String taskItemToStorageFormat(TaskItem task) {
		String storageFormat = "\"" + task.getDescription() + "\"";
		return storageFormat;
	}

	private TaskItem storageFormatToTaskItem(String storageFormat) {
		TaskItem task = new TaskItem();
		String description = storageFormat.substring(1,
				storageFormat.lastIndexOf('\"'));
		task.setDescription(description);
		return task;
	}

	public void clear() {
		tasks.clear();
		writeTasksToFile(tasks);
	}
}
