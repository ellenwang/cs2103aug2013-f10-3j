package Logic;

import java.util.Vector;

import TaskItem.TaskItem;

public class Logic {
	Vector<TaskItem> allTasks;
	TaskItem lastModifiedTask;
	TaskItem lastCreateTask;
	TaskItem lastDeleteTask;
	CommandStack commandlist = new CommandStack();
	
	public void executeCommand(){
		commandlist.peekLatestCommand().executeCommand();
	}

	public Vector<TaskItem> getAllTasks() {
		return allTasks;
	}

	public void setAllTasks(Vector<TaskItem> allTasks) {
		this.allTasks = allTasks;
	}

	public TaskItem getLastModifiedTask() {
		return lastModifiedTask;
	}

	public void setLastModifiedTask(TaskItem lastModifiedTask) {
		this.lastModifiedTask = lastModifiedTask;
	}

	public TaskItem getLastCreateTask() {
		return lastCreateTask;
	}

	public void setLastCreateTask(TaskItem lastCreateTask) {
		this.lastCreateTask = lastCreateTask;
	}

	public TaskItem getLastDeleteTask() {
		return lastDeleteTask;
	}

	public void setLastDeleteTask(TaskItem lastDeleteTask) {
		this.lastDeleteTask = lastDeleteTask;
	}
}