package Command;

import java.util.Vector;

import TaskItem.TaskItem;

public class ViewCommand extends Command{
	public ViewCommand(String commandType, Vector<String> commandParameters) {
		super("view", commandParameters);
	}
	private boolean redoable = false;
	private boolean undoable = false;

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
	private static String executeViewCommand(Command command) {
		String regex = "[0-9]+";
		String range = command.getCommandParameters().get(0);
		String result = "";
		try {
			if (range.equals("all")) {
				Vector<TaskItem> firstPriorityTasks = new Vector<TaskItem>();
				Vector<TaskItem> secondPriorityTasks = new Vector<TaskItem>();
				Vector<TaskItem> thirdPriorityTasks = new Vector<TaskItem>();
				for (int i = 0; i < allTasks.size(); i++) {
					TaskItem currentTask = allTasks.get(i);
					if (currentTask.getPriority() == 1) {
						firstPriorityTasks.add(currentTask);
					} else if (currentTask.getPriority() == 2) {
						secondPriorityTasks.add(currentTask);
					} else if (currentTask.getPriority() == 3) {
						thirdPriorityTasks.add(currentTask);
					}
				}

				matchingTasks.clear();
				matchingTasks.addAll(firstPriorityTasks);
				matchingTasks.addAll(secondPriorityTasks);
				matchingTasks.addAll(thirdPriorityTasks);
				result = vectorToString(matchingTasks);
			} else if (range.equals("finished")) {
				matchingTasks.clear();
				for (int i = 0; i < allTasks.size(); i++) {
					TaskItem currentTask = allTasks.get(i);
					currentTask.updateStatus();
					if (currentTask.getStatus() == TaskItem.Status.FINISHED) {
						matchingTasks.add(currentTask);
					}
				}
				result = vectorToString(matchingTasks);
			} else if (range.equals("expired")) {
				matchingTasks.clear();
				for (int i = 0; i < allTasks.size(); i++) {
					TaskItem currentTask = allTasks.get(i);
					currentTask.updateStatus();
					if (currentTask.getStatus() == TaskItem.Status.EXPIRED) {
						matchingTasks.add(currentTask);
					}
				}
				result = vectorToString(matchingTasks);
			} else if (range.equals("unfinished")) {
				matchingTasks.clear();
				for (int i = 0; i < allTasks.size(); i++) {
					TaskItem currentTask = allTasks.get(i);
					currentTask.updateStatus();
					if (currentTask.getStatus() == TaskItem.Status.UNFINISHED) {
						matchingTasks.add(currentTask);
					}
				}
				result = vectorToString(matchingTasks);
			} else if (isNumeric(range)) {
				matchingTasks.clear();

				int index = Integer.parseInt(range);
				matchingTasks.add(allTasks.get(index-1));
				result = vectorToString(matchingTasks);
			}
		} catch (Exception e) {
			result = String.format(MESSAGE_INVALID_COMMAND_PARAMETER, command
					.getCommandParameters().get(0))
					+ "System message" + e.getMessage();
		}
		return result;
	}
}
