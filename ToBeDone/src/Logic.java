import java.util.Vector;

public class Logic {
	// messages to the user
	private static final String MESSAGE_EXIT = "Exiting ToBeDone.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command: \"%1$s\"";
	private static final String MESSAGE_FAILED_TO_UNDO = "Failed to undo command: \"%1$s\"";
	private static final String MESSAGE_FAILED_TO_REDO = "Failed to redo command: \"%1$s\"";
	
	// all task items
	private static Vector<TaskItem> allTaskItems;

	// task items matching the latest view or search command
	private static Vector<TaskItem> matchingTaskItems;

	// variables used for undo and redo command
	private static Command lastModifyingCommand;
	private static Command lastUndoneCommand;
	private static Command lastRedoneCommand;
	private static TaskItem lastCreatedTask;
	private static TaskItem lastDeletedTask;
	private static TaskItem lastUpdatedTask;

	public static String executeCommand(Command command) {
		String commandType = command.getCommandType();
		switch (commandType) {
		case "create":
			return executeCreateCommand(command);
		case "view":
			return executeViewCommand(command);
		case "exit":
			return executeExitCommand();
		case "update":
			return executeUpdateCommand(command);
		case "delete":
			return executeDeleteCommand(command);
		case "search":
			return executeSearchCommand(command);
		case "undo":
			return executeUndoCommand();
		case "redo":
			return executeRedoCommand();
		default:
			return String.format(MESSAGE_INVALID_COMMAND, commandType);
		}
	}

	private static String executeCreateCommand(Command command) {
		// TODO
		return "";
	}

	private static String executeViewCommand(Command command) {
		// TODO
		return "";
	}

	private static String executeUpdateCommand(Command command) {
		// TODO
		return "";
	}

	private static String executeDeleteCommand(Command command) {
		// TODO
		return "";
	}

	private static String executeSearchCommand(Command command) {
		String keyword = command.getCommandParameters().get(0);
		String result = "";
		for (int i=0; i<allTaskItems.size(); i++){
			String taskInfo = allTaskItems.get(i).toString();
			TaskItem currentItem = allTaskItems.get(i);
			if (taskInfo.contains(keyword)) {
				matchingTaskItems.add(currentItem);
			}
		}	
		result = vectorToString(matchingTaskItems);
		return result;
	}

	private static String executeUndoCommand() {
		// TODO
		return "";
	}
	
	private static String executeRedoCommand() {
		// TODO
		return "";
	}
	
	private static String executeExitCommand() {
		return MESSAGE_EXIT;
	}

	private static int indexToTaskID(int index) {
		TaskItem task = matchingTaskItems.get(index - 1);
		return task.getTaskID();
	}
	private static String vectorToString (Vector<TaskItem> list) {
		String result = "";
		for (int i=0; i<list.size(); i++){
			result += (i+1) + "" + '.' +list.get(i).toString()+'\n';
		}
		return result;
	}
}
