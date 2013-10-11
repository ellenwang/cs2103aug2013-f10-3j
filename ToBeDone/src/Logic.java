import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Logic {
	// messages to the user
	private static final String MESSAGE_EXIT = "Exiting ToBeDone.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command: \"%1$s\".";
	private static final String MESSAGE_INVALID_COMMAND_PARAMETER = "Invalid command parameter: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_UNDO = "Failed to undo command: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_UNDO_TWICE = "Failed to undo command twice, to undo any changes made by undo use the redo command.";
	private static final String MESSAGE_FAILED_TO_REDO = "Failed to redo command: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_REDO_WITHOUT_UNDO = "Failed to redo since the last modifying command is not undo.";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "Deleted task: \"%1$s\".";
	private static final String MESSAGE_CREATE_SUCCESSFUL = "Created task: \"%1$s\".";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Updated task: \"%1$s\".";
	private static final String MESSAGE_WRONG_TIME_FORMAT = "Wrong time format.";
	private static final String MESSAGE_TOO_MANY_PARAMETERS = "Wrong command format. Too many parameters";
	private static final String MESSAGE_ENDTIME_SMALLER_THAN_STARTTIME = "The end time of tasks can't be before the start time";

	// task types
	private static final int FLOATING_TASK = 2;
	private static final int DEADLINE_TASK = 3;;
	private static final int TIMED_TASK = 4;

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM,HH:mm");

	// all tasks
	private static Vector<TaskItem> allTasks = Storage.retrieve();

	// tasks matching the latest view or search command
	private static Vector<TaskItem> matchingTasks = new Vector<TaskItem>();

	// variables used for undo and redo command
	private static Command lastModifyingCommand;
	private static Command lastRedoneCommand;
	private static Command lastUndoneCommand;
	private static TaskItem lastCreatedTask;
	private static TaskItem lastDeletedTask;
	private static TaskItem lastUpdatedTask;

  static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd\\MM','HH:mm");
  
	public static void init() {
		updateTaskIDs();
	}

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
		// / since there are 3 kind of task. each has different number of
		// parameters
		Vector<String> Para = command.getCommandParameters();
		int taskType = Para.size();

		String taskDescription = Para.get(0);
		Date taskStartTime = null;
		Date taskEndTime = null;
		int priority = 1;

		// a floating task, just has description and priority
		if (taskType == FLOATING_TASK) {
			priority = Integer.parseInt(Para.get(1));
		}

		// a task just has description\endTime and priority
		if (taskType == DEADLINE_TASK) {
			try {
				taskEndTime = simpleDateFormat.parse(Para.get(1));
			} catch (ParseException e) {
				return MESSAGE_WRONG_TIME_FORMAT;
			}
			priority = Integer.parseInt(Para.get(2));
		}

		// a full task with description\startTime\endTime and priority
		if (taskType == TIMED_TASK) {
			try {
				taskStartTime = simpleDateFormat.parse(Para.get(1));
				taskEndTime = simpleDateFormat.parse(Para.get(2));
			} catch (ParseException e) {
				return MESSAGE_WRONG_TIME_FORMAT;
			}

			if (taskEndTime.before(taskStartTime)) {
				return MESSAGE_ENDTIME_SMALLER_THAN_STARTTIME;
			}

			priority = Integer.parseInt(Para.get(3));
		}

		// a create command has 3 parameters at most
		if (taskType > TIMED_TASK) {
			return MESSAGE_TOO_MANY_PARAMETERS;
		}

		TaskItem newItem = new TaskItem(taskDescription, taskStartTime,
				taskEndTime, priority);
		allTasks.add(newItem);
		updateTaskIDs();
		Storage.store(allTasks);
		lastModifyingCommand = command;
		return String.format(MESSAGE_CREATE_SUCCESSFUL, taskDescription);
	}

	private static String executeViewCommand(Command command) {

		String range = command.getCommandParameters().get(0);
		String result = "";
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
		} else {
			result = String.format(MESSAGE_INVALID_COMMAND_PARAMETER, command
					.getCommandParameters().get(0));
		}
		return result;
	}

private static String executeUpdateCommand(Command command) {
		int index = Integer.parseInt(command.getCommandParameters().get(0));
		int taskID = indexToTaskID(index);
		TaskItem updatedTask = allTaskItems.get(taskID);
		lastUpdatedTask = updatedTask;
		
		for (int i = 1; i < command.getCommandParameters().capacity(); i++) {
			String parameter = command.getCommandParameters().get(i);
			if (parameter.startsWith("\"")) {
				updatedTask.setDescription(parameter);
			}
			
			if(parameter.startsWith("(start)")){
				String newStartTimeString = parameter.substring(7);
				try {
					updatedTask.setStartTime(simpleDateFormat.parse(newStartTimeString));
				} catch (ParseException e) {
					return MESSAGE_WRONG_TIME_FORMAT;
				}
				
			}
			
			if (parameter.startsWith("(end)")) {
				String newEndTimeString = parameter.substring(5);
				try {
					updatedTask.setEndTime(simpleDateFormat.parse(newEndTimeString));
				} catch (ParseException e) {
					return MESSAGE_WRONG_TIME_FORMAT;
				}
			}
			
			if (parameter.length() == 1) {
				updatedTask.setPriority(Integer.parseInt(parameter));
			}
		}
		return UPDATE_SUCCESSFULLY;
	}


	private static String executeDeleteCommand(Command command) {
		String indexToDelete = command.getCommandParameters().get(0);
		int index = Integer.parseInt(indexToDelete);
		int taskID = indexToTaskID(index);
		lastDeletedTask = allTasks.remove(taskID);
		updateTaskIDs();
		Storage.store(allTasks);
		lastModifyingCommand = command;
		String description = lastDeletedTask.getDescription();
		return String.format(MESSAGE_DELETE_SUCCESSFUL, description);
	}

	private static String executeSearchCommand(Command command) {
		matchingTasks.clear();

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

	private static String executeUndoCommand() {
		if (lastModifyingCommand.getCommandType().equals("undo")) {
			return MESSAGE_FAILED_TO_UNDO_TWICE;
		}

		Command lastCommand;
		if (lastModifyingCommand.getCommandType().equals("redo")) {
			lastCommand = lastRedoneCommand;
		} else {
			lastCommand = lastModifyingCommand;
		}

		String feedback;
		String description;

		String lastCommandType = lastCommand.getCommandType();

		switch (lastCommandType) {
		case "create":
			description = lastCreatedTask.getDescription();
			lastDeletedTask = allTasks.remove(lastCreatedTask.getTaskID());
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String.format(MESSAGE_DELETE_SUCCESSFUL, description);
			break;
		case "delete":
			description = lastDeletedTask.getDescription();
			lastCreatedTask = lastDeletedTask;
			allTasks.add(lastDeletedTask.getTaskID(), lastDeletedTask);
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String.format(MESSAGE_CREATE_SUCCESSFUL, description);
			break;
		case "update":
			description = lastUpdatedTask.getDescription();
			lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
					lastUpdatedTask);
			Storage.store(allTasks);
			feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL, description);
			break;
		default:
			feedback = String.format(MESSAGE_FAILED_TO_UNDO, lastCommandType);
		}

		lastUndoneCommand = lastCommand;
		lastModifyingCommand = new Command("undo", null);

		return feedback;
	}

	public static String executeRedoCommand() {
		if (!lastModifyingCommand.getCommandType().equals("undo")) {
			return MESSAGE_FAILED_TO_REDO_WITHOUT_UNDO;
		}

		Command lastCommand;
		if (lastModifyingCommand.getCommandType().equals("undo")) {
			lastCommand = lastUndoneCommand;
		} else {
			lastCommand = lastModifyingCommand;
		}

		String feedback;
		String description;
		String lastCommandType = lastCommand.getCommandType();

		switch (lastCommandType) {
		case "create":
			description = lastCreatedTask.getDescription();
			allTasks.add(lastCreatedTask.getTaskID(), lastCreatedTask);
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String.format(MESSAGE_CREATE_SUCCESSFUL, description);
			break;
		case "delete":
			description = lastDeletedTask.getDescription();
			allTasks.remove(lastDeletedTask.getTaskID());
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String.format(MESSAGE_DELETE_SUCCESSFUL, description);
			break;
		case "update":
			description = lastUpdatedTask.getDescription();
			lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
					lastUpdatedTask);
			feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL, description);
			break;
		default:
			feedback = String.format(MESSAGE_FAILED_TO_REDO, lastCommandType);
		}

		lastRedoneCommand = lastCommand;
		lastModifyingCommand = new Command("redo", null);

		return feedback;
	}

	private static String executeExitCommand() {
		return MESSAGE_EXIT;
	}

	private static void updateTaskIDs() {
		for (int i = 0; i < allTasks.size(); i++) {
			allTasks.get(i).setTaskID(i);
		}
	}

	private static int indexToTaskID(int index) {
		TaskItem task = matchingTasks.get(index - 1);
		return task.getTaskID();
	}

	private static String vectorToString(Vector<TaskItem> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + ". " + list.get(i) + '\n';
		}
		return result;
	}
}
