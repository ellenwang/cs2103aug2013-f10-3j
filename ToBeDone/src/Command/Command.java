package Command;

import java.util.Vector;

import Logic.CommandStack;
import Storage.Storage;
import TaskItem.TaskItem;


public class Command {
	private String commandType;
	private Vector<String> commandParameters;
	protected static Vector<TaskItem> allTasks = Storage.retrieve();
	protected static TaskItem lastCreatedTask;
	protected static TaskItem lastDeletedTask;
	protected static Vector<TaskItem> lastDeletedTasks = new Vector<TaskItem>();
	protected static Command lastCommand;
	protected static TaskItem lastUpdatedTask;

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
	public int indexToTaskID(int index) {
		TaskItem task = matchingTasks.get(index - 1);
		return task.getTaskID();
	}

	public String vectorToString(Vector<TaskItem> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + ". " + list.get(i) + '\n';
		}

		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}
	public String executeCommand(){
		Command.execute();
	}
	public String undo(){
		CommandStack commandlist = new CommandStack();
		String feedback;
		if (!commandlist.getCommandStack().empty()){
			Command currentCommand = commandlist.getLatestCommand();
			
			feedback = currentCommand.undo();
		} else {
			feedback = "No more command to undo.";
		}
		
		return feedback;
	}
	public String redo(){
		CommandStack commandlist = new CommandStack();
		String feedback;
		if (!commandlist.getCommandStack().empty()){
			Command currentCommand = commandlist.getLatestCommand();
			feedback = currentCommand.undo();
		} else {
			feedback = "No more command to redo.";
		}
		
		return feedback;
	}
}
