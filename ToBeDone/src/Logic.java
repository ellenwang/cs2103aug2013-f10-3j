import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {
	// messages to the user
	private static final String MESSAGE_EXIT = "Exiting ToBeDone.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command: \"%1$s\".";
	private static final String MESSAGE_INVALID_COMMAND_PARAMETER = "Invalid command parameter: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_UNDO = "Failed to undo command: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_UNDO_TWICE = "Failed to undo command twice, to undo any changes made by undo use the redo command.";
	private static final String MESSAGE_NO_COMMAND_TO_UNDO = "Failed to undo as no modifying command has been executed.";
	private static final String MESSAGE_FAILED_TO_REDO = "Failed to redo command: \"%1$s\".";
	private static final String MESSAGE_FAILED_TO_REDO_WITHOUT_UNDO = "Failed to redo since the last modifying command is not undo.";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	private static final String MESSAGE_DELETE_ALL_SUCCESSFUL = "Deleted all tasks.";
	private static final String MESSAGE_NO_TASKS_TO_DELETE = "No tasks to delete.";
	private static final String MESSAGE_NO_TASKS_TO_RESTORE = "No tasks to restore.";
	private static final String MESSAGE_TASKS_RESTORED = "All tasks has been restored.";
	private static final String MESSAGE_CREATE_SUCCESSFUL = "Created task: %1$s";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Old task:\t%1$s\nUpdated task:\t%2$s";
    private static final String MESSAGE_FINISH_SUCCESSFUL = "Finished task: \"%1$s\".";
	private static final String MESSAGE_WRONG_TIME_FORMAT = "Wrong time format.";
	private static final String MESSAGE_TOO_MANY_PARAMETERS = "Wrong command format. Too many parameters.";
	private static final String MESSAGE_ENDTIME_SMALLER_THAN_STARTTIME = "The end time of tasks can't be before the start time.";
	private static final String MESSAGE_SYNC_SUCCESS="Synchronize succeed.";
	// task types
	private static final int FLOATING_TASK = 2;
	private static final int DEADLINE_TASK = 3;;
	private static final int TIMED_TASK = 4;

	// logger
	private static Logger logger = Logger.getLogger("Logic_logger");

	// set logging level
	static {
		logger.setLevel(Level.WARNING);
	}

  private static int CURRENT_YEAR;
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM,HH:mmyyyy");

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
	private static Vector<TaskItem> lastDeletedTasks = new Vector<TaskItem>();
	private static TaskItem lastUpdatedTask;

	public static void init() {
		updateTaskIDs();
		CURRENT_YEAR = (Calendar.getInstance()).get(Calendar.YEAR);
	}

	public static String executeCommand(Command command) {
    init();
		String commandType = command.getCommandType();
		logger.log(Level.INFO, "The execution of command starts here.");
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
		case "finish":
			return executeFinshCommand(command);
		case "undo":
			return executeUndoCommand();
		case "redo":
			return executeRedoCommand();
		default:
			return String.format(MESSAGE_INVALID_COMMAND, commandType);
		}
	}

  private static String executeFinshCommand(Command command) {
		String indexToFinish = command.getCommandParameters().get(0);
		int index = Integer.parseInt(indexToFinish);
		int taskID = indexToTaskID(index);
		TaskItem finshedTaskItem = allTasks.get(taskID);

		finshedTaskItem.setStatus(TaskItem.Status.FINISHED);
		updateTaskIDs();
		Storage.store(allTasks);
    lastModifyingCommand = command;

		return String.format(MESSAGE_FINISH_SUCCESSFUL, finshedTaskItem.getDescription());
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
				taskEndTime = simpleDateFormat.parse(Para.get(1)+CURRENT_YEAR);
			} catch (ParseException e) {
				return MESSAGE_WRONG_TIME_FORMAT;
			}
			priority = Integer.parseInt(Para.get(2));
		}

		// a full task with description\startTime\endTime and priority
		if (taskType == TIMED_TASK) {
			try {
				taskStartTime = simpleDateFormat.parse(Para.get(1)+CURRENT_YEAR);
				taskEndTime = simpleDateFormat.parse(Para.get(2)+CURRENT_YEAR);
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
		lastCreatedTask = newItem;
		return String.format(MESSAGE_CREATE_SUCCESSFUL, newItem);
	}

	private static String executeViewCommand(Command command) {

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
     }else if (range.equals("unfinished")) {
				matchingTasks.clear();
				for (int i = 0; i < allTasks.size(); i++) {
					TaskItem currentTask = allTasks.get(i);
					currentTask.updateStatus();
					if (currentTask.getStatus() == TaskItem.Status.UNFINISHED) {
						matchingTasks.add(currentTask);
					}
				}
				result = vectorToString(matchingTasks);
			}
		} catch (Exception e) {
			result = String.format(MESSAGE_INVALID_COMMAND_PARAMETER, command
					.getCommandParameters().get(0)) + "System message" + e.getMessage();
		}
		return result;
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
							.parse(newStartTimeString+CURRENT_YEAR));
				} catch (ParseException e) {
					return MESSAGE_WRONG_TIME_FORMAT;
				}

			}

			else if (parameter.startsWith("(end)")) {
				String newEndTimeString = parameter.substring(5);
				try {
					updatedTask.setEndTime(simpleDateFormat
							.parse(newEndTimeString+CURRENT_YEAR));
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

	private static String executeSearchCommand(Command command) {
		matchingTasks.clear();
		assert command.getCommandParameters().size()==1;
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
		if (lastModifyingCommand == null) {
			return MESSAGE_NO_COMMAND_TO_UNDO;
		}

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

		String lastCommandType = lastCommand.getCommandType();

		switch (lastCommandType) {
		case "create":
			lastDeletedTask = allTasks.remove(lastCreatedTask.getTaskID());
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String
					.format(MESSAGE_DELETE_SUCCESSFUL, lastCreatedTask);
			break;
		case "delete":
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
			break;
		case "update":
			lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
					lastUpdatedTask);
			Storage.store(allTasks);
			TaskItem updatedTask = allTasks.get(lastUpdatedTask.getTaskID());
			feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL,
					lastUpdatedTask, updatedTask);
			break;
		default:
			feedback = String.format(MESSAGE_FAILED_TO_UNDO, lastCommandType);
		}

		lastUndoneCommand = lastCommand;
		lastModifyingCommand = new Command("undo", null);

		return feedback;
	}

	public static String executeRedoCommand() {
		if (lastModifyingCommand == null
				|| !lastModifyingCommand.getCommandType().equals("undo")) {
			return MESSAGE_FAILED_TO_REDO_WITHOUT_UNDO;
		}

		Command lastCommand;
		if (lastModifyingCommand.getCommandType().equals("undo")) {
			lastCommand = lastUndoneCommand;
		} else {
			lastCommand = lastModifyingCommand;
		}

		String feedback;
		String lastCommandType = lastCommand.getCommandType();

		switch (lastCommandType) {
		case "create":
			allTasks.add(lastCreatedTask.getTaskID(), lastCreatedTask);
			updateTaskIDs();
			Storage.store(allTasks);
			feedback = String
					.format(MESSAGE_CREATE_SUCCESSFUL, lastCreatedTask);
			break;
		case "delete":
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

			break;
		case "update":
			lastUpdatedTask = allTasks.set(lastUpdatedTask.getTaskID(),
					lastUpdatedTask);
			Storage.store(allTasks);
			TaskItem updatedTask = allTasks.get(lastUpdatedTask.getTaskID());
			feedback = String.format(MESSAGE_UPDATE_SUCCESSFUL,
					lastUpdatedTask, updatedTask);
			break;
		default:
			feedback = String.format(MESSAGE_FAILED_TO_REDO, lastCommandType);
		}

		lastRedoneCommand = lastCommand;
		lastModifyingCommand = new Command("redo", null);

		return feedback;
	}

	public static String executeSync(){
		return MESSAGE_SYNC_SUCCESS;
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

		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}
}