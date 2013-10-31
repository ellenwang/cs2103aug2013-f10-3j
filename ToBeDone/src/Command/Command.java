package Command;

import java.util.Vector;

import Storage.Storage;
import TaskItem.TaskItem;


public class Command {
	private String commandType;
	private Vector<String> commandParameters;
	protected static Vector<TaskItem> allTasks = Storage.retrieve();
	protected static TaskItem lastCreatedTask;
	protected static TaskItem lastDeletedTask;

	public Command(String commandType, Vector<String> commandParameters) {
		this.commandType = commandType;
		this.commandParameters = commandParameters;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public Vector<String> getCommandParameters() {
		return commandParameters;
	}

	public void setCommandParameters(Vector<String> commandParameters) {
		this.commandParameters = commandParameters;
	}
	public void updateTaskIDs() {
		for (int i = 0; i < allTasks.size(); i++) {
			allTasks.get(i).setTaskID(i);
		}
	}
}
