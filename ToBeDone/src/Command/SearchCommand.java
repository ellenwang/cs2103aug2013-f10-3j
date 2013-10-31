package Command;

import java.util.Vector;

import TaskItem.TaskItem;

public class SearchCommand extends Command {
	public SearchCommand(String commandType, Vector<String> commandParameters) {
		super("search", commandParameters);
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
	private static String executeSearchCommand(Command command) {
		matchingTasks.clear();
		assert command.getCommandParameters().size() == 1;
		String keyword = command.getCommandParameters().get(0);
		String result = "";
		for (int i = 0; i < allTasks.size(); i++) {
			String taskInfo = allTasks.get(i).toString();
			TaskItem currentItem = allTasks.get(i);
			if (taskInfo.contains(keyword)) {
				matchingTasks.add(currentItem);
			}
		}
		result = vectorToString(matchingTasks);
		return result;
	}
}
