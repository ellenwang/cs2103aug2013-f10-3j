package Command;

import java.util.Vector;

import Storage.Storage;
import TaskItem.TaskItem;

public class DeleteCommand extends Command{
	public DeleteCommand(String commandType, Vector<String> commandParameters) {
		super("delete", commandParameters);
	}
	private boolean redoable = true;
	private boolean undoable = true;
	private static final String MESSAGE_TASKS_RESTORED = "All tasks has been restored.";
	private static final String MESSAGE_NO_TASKS_TO_RESTORE = "No tasks to restore.";
	private static final String MESSAGE_CREATE_SUCCESSFUL = "Created task: %1$s";
	private static final String MESSAGE_DELETE_ALL_SUCCESSFUL = "Deleted all tasks.";
	private static final String MESSAGE_NO_TASKS_TO_DELETE = "No tasks to delete.";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	
	

	public boolean isRedoable() {
		return redoable;
	}
	public void setRedoable(boolean redoable) {
		this.redoable = redoable;
	}
	public boolean isUndoable() {
		return undoable;
	}
	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}
	public String undo(){
		String feedback="";
		String lastCommandParameter = lastCommand.getCommandParameters()
				.get(0);
		if (lastCommandParameter.equals("all")) {
			if (lastDeletedTasks.size() != 0) {
				allTasks = new Vector<TaskItem>(lastDeletedTasks);
				feedback = MESSAGE_TASKS_RESTORED;
			} else {
				feedback = MESSAGE_NO_TASKS_TO_RESTORE;
			}
		} else {
			lastCreatedTask = lastDeletedTask;
			allTasks.add(lastDeletedTask.getTaskID(), lastDeletedTask);
			updateTaskIDs();
			feedback = String.format(MESSAGE_CREATE_SUCCESSFUL,
					lastDeletedTask);
		}
		Storage.store(allTasks);
		return feedback;
	}
	public String redo(){
		String feedback;
		String lastCommandParameter = lastCommand.getCommandParameters()
				.get(0);
		if (lastCommandParameter.equals("all")) {
			if (allTasks.size() != 0) {
				allTasks.clear();
				feedback = MESSAGE_DELETE_ALL_SUCCESSFUL;
			} else {
				feedback = MESSAGE_NO_TASKS_TO_DELETE;
			}
		} else {
			allTasks.remove(lastDeletedTask.getTaskID());
			updateTaskIDs();
			feedback = String.format(MESSAGE_DELETE_SUCCESSFUL,
					lastDeletedTask);
		}
		Storage.store(allTasks);
		return feedback;
	}
	
	private static String executeDeleteCommand(Command command) {
		String feedback;
		assert command.getCommandParameters().size() == 1;
		String commandParameter = command.getCommandParameters().get(0);

		if (commandParameter.equals("all")) {
			if (allTasks.size() != 0) {
				lastDeletedTasks = new Vector<TaskItem>(allTasks);
				allTasks.clear();
				feedback = MESSAGE_DELETE_ALL_SUCCESSFUL;
			} else {
				feedback = MESSAGE_NO_TASKS_TO_DELETE;
			}
		} else {
			int index = Integer.parseInt(commandParameter);
			int taskID = indexToTaskID(index);
			lastDeletedTask = allTasks.remove(taskID);
			updateTaskIDs();
			feedback = String
					.format(MESSAGE_DELETE_SUCCESSFUL, lastDeletedTask);
		}

		Storage.store(allTasks);
		lastModifyingCommand = command;
		return feedback;
	}

}
