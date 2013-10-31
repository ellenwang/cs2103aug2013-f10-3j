package Command;

import java.text.ParseException;
import java.util.Vector;

import Storage.Storage;
import TaskItem.TaskItem;

public class UpdateCommand extends Command{
	public UpdateCommand(String commandType, Vector<String> commandParameters) {
		super("update", commandParameters);
	}
	private boolean redoable = true;
	private boolean undoable = true;
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Old task:\t%1$s\nUpdated task:\t%2$s";
	
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
		String feedback;
		lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
				lastUpdatedTask);
		Storage.store(allTasks);
		TaskItem updatedTask = allTasks.get(lastUpdatedTask.getTaskID());
		feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL,
				lastUpdatedTask, updatedTask);
		return feedback;
	}
	public String redo(){
		String feedback;
		lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
				lastUpdatedTask);
		Storage.store(allTasks);
		TaskItem updatedTask = allTasks.get(lastUpdatedTask.getTaskID());
		feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL,
				lastUpdatedTask, updatedTask);
		return feedback;
	}
	private static String executeUpdateCommand(Command command) {
		int index = Integer.parseInt(command.getCommandParameters().get(0));
		int taskID = indexToTaskID(index);
		TaskItem updatedTask = allTasks.get(taskID);
		lastUpdatedTask = new TaskItem(updatedTask);
		for (int i = 1; i < command.getCommandParameters().size(); i++) {
			String parameter = command.getCommandParameters().get(i);

			if (parameter.startsWith("(start)")) {
				String newStartTimeString = parameter.substring(7);
				try {
					updatedTask.setStartTime(simpleDateFormat
							.parse(newStartTimeString + CURRENT_YEAR));
				} catch (ParseException e) {
					return MESSAGE_WRONG_TIME_FORMAT;
				}

			}

			else if (parameter.startsWith("(end)")) {
				String newEndTimeString = parameter.substring(5);
				try {
					updatedTask.setEndTime(simpleDateFormat
							.parse(newEndTimeString + CURRENT_YEAR));
				} catch (ParseException e) {
					return MESSAGE_WRONG_TIME_FORMAT;
				}
			}

			else if (parameter.length() == 1) {
				updatedTask.setPriority(Integer.parseInt(parameter));
			}

			else {
				updatedTask.setDescription(parameter);
			}

		}

		updateTaskIDs();
		Storage.store(allTasks);
		lastModifyingCommand = command;
		return String.format(MESSAGE_UPDATE_SUCCESSFUL, lastUpdatedTask,
				updatedTask);
	}

}
