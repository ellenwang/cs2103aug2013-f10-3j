package Command;

import java.util.Vector;

import Storage.Storage;

public class CreateCommand extends Command {

	public CreateCommand(String commandType, Vector<String> commandParameters) {
		super("create", commandParameters);
	}
	private boolean redoable = true;
	private boolean undoable = true;
	private static final String MESSAGE_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	private static final String MESSAGE_CREATE_SUCCESSFUL = "Created task: %1$s";

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
		lastDeletedTask = allTasks.remove(lastCreatedTask.getTaskID());
		updateTaskIDs();
		Storage.store(allTasks);
		String feedback = String
				.format(MESSAGE_DELETE_SUCCESSFUL, lastCreatedTask);
		return feedback;
	}
	public String redo(){
		allTasks.add(lastCreatedTask.getTaskID(), lastCreatedTask);
		updateTaskIDs();
		Storage.store(allTasks);
		String feedback = String
				.format(MESSAGE_CREATE_SUCCESSFUL, lastCreatedTask);
		return feedback;
	}
}
