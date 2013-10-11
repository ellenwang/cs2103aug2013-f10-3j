import java.util.Date;
import java.util.Vector;

public class Logic {
	private static final String CREATED_FAIL_MESSAGE = "Creating new task failed.";
	private static final String MARKED_AS_DONE = "The task is marked as done.";
	private static final String MARKED_AS_UNDONE = "The task is marked as undone.";
	private static final String CREATED_SUCCESS_MESSAGE = "New task has been created.";
	private static final String DELETED_SUCCESS_MESSAGE = "The task is deleted successfully.";
	private static final String UPDATE_SUCCESS_MESSAGE = "The task is updated successfully.";
	private static final String INVALID_ITEM = "Item not found.";
	private static final String NO_SEARCH_RESULT = "No such task item.";
	private static final String DELETED_Fail_MESSAGE = "Fail to delete.";
	private static final String UNDO_SUCCESS = "The command has been undone.";
	private static final String UNDO_FAILED = "Could not undo command.";
	private static final String REDO_SUCCESS = "The command has been redone.";
	private static final String REDO_FAILED = "Could not redo command.";
	private static Vector<TaskItem> aimTasks;

	// variables used for undo
	private static TaskItem lastCreatedTask;
	private static TaskItem lastDeletedTask;
	private static TaskItem lastUpdatedTask;
	private static String lastUndoneCommand;
	private static String lastRedoneCommand;

	static enum LAST_COMMAND {
		create, delete, update, search, view, undo
	};

	static enum STATUS {
		finished, unfinished, expired
	};

	static String createTask(String description, Date startTime, Date endTime,
			int priority) {
		try {

			TaskItem createNewOne = new TaskItem(description, startTime,
					endTime, priority);
			lastCreatedTask = Storage.store(createNewOne);
		} catch (Exception e) {
			return CREATED_FAIL_MESSAGE;
		}
		return CREATED_SUCCESS_MESSAGE;
	}

	static String markTask(int index) {
		TaskItem toMark;
		int TaskID = indexToTaskID(index);
		try {
			toMark = Storage.retrieve(TaskID);
		} catch (Exception e) {
			return INVALID_ITEM;
		}
		toMark.setStatus(2);
		return MARKED_AS_DONE;
	}

	static String unmarkTask(int index) {
		TaskItem toUnMark;
		int TaskID = indexToTaskID(index);
		try {
			toUnMark = Storage.retrieve(TaskID);
		} catch (Exception e) {
			return INVALID_ITEM;
		}
		toUnMark.setStatus(1);
		return MARKED_AS_UNDONE;
	}

	static String updateTask(int index, String description, Date startTime,
			Date endTime, int priority) {
		TaskItem toUpdate;
		int TaskID = indexToTaskID(index);
		try {
			toUpdate = Storage.delete(TaskID);
		} catch (Exception e) {
			return INVALID_ITEM;
		}
		if (description != null) {
			toUpdate.setDescription(description);
		}
		if (startTime != null) {
			toUpdate.setStartTime(startTime);
		}
		if (endTime != null) {
			toUpdate.setEndTime(endTime);
		}
		if (priority != -1) {
			toUpdate.setPriority(priority);
		}
		
		lastUpdatedTask = Storage.store(toUpdate.getTaskID(), toUpdate);
		return UPDATE_SUCCESS_MESSAGE;
	}

	static String searchTask(String keyword) {
		String searchResult = "Search results are as follow:\n";
		String taskItemString;

		aimTasks = Storage.search(keyword);

		if (aimTasks.size() == 0) {
			return NO_SEARCH_RESULT;
		}

		for (int i = 0; i < aimTasks.size(); i++) {
			taskItemString = (i + 1) + "." + aimTasks.get(i);
			searchResult += taskItemString + "\n";
		}
		return searchResult;
	}

	static int indexToTaskID(int index) {
		TaskItem temp = aimTasks.get(index - 1);
		return temp.getTaskID();
	}

	static String viewAll() {
		String result = "";
		aimTasks = Storage.retrieveAll();
		for (int i = 0; i < aimTasks.size(); i++) {
			result += (i+1) + "" + '.' +aimTasks.get(i).toString()+'\n';
		}
		return result;
	}

	static String viewTask(int index) {
		int taskID = indexToTaskID(index);
		String result = Storage.retrieve(taskID).toString();
		return result;
	}

	static String viewFinished() {
		String result = "";
		Vector<TaskItem> finishedList = Storage.retrieveFinished();
		for (int i = 0; i < finishedList.size(); i++) {
			result += finishedList.get(i).toString();
		}
		return result;
	}

	static String viewUnfinished() {
		String result = "";
		Vector<TaskItem> unfinishedList = Storage.retrieveUnfinished();
		for (int i = 0; i < unfinishedList.size(); i++) {
			result += unfinishedList.get(i).toString();
		}
		return result;
	}

	static String deleteTask(int index) {
		int taskID = indexToTaskID(index);
		TaskItem taskDeleted = Storage.delete(taskID);
		lastDeletedTask = taskDeleted;
		if (taskDeleted != null) {
			return DELETED_SUCCESS_MESSAGE;
		} else {
			return DELETED_Fail_MESSAGE;
		}
	}

	public static String undo(String lastExecutedCommand) {
		if (lastExecutedCommand.equals("undo")) {
			return UNDO_FAILED;
		}
		
		String feedback;
		String lastCommand;
		if (lastExecutedCommand.equals("redo")) {
			lastCommand = lastRedoneCommand;
		} else {
			lastCommand = lastExecutedCommand;
		}
		
		switch (lastCommand) {
		case "create":
			Storage.delete(lastCreatedTask.getTaskID());
			feedback = UNDO_SUCCESS;
			break;
		case "delete":
			Storage.store(lastDeletedTask.getTaskID(), lastDeletedTask);
			feedback = UNDO_SUCCESS;
			break;
		case "update":
			Storage.delete(lastUpdatedTask.getTaskID());
			Storage.store(lastUpdatedTask.getTaskID(), lastUpdatedTask);
			feedback = UNDO_SUCCESS;
			break;
		default:
			feedback = UNDO_FAILED;
		}
		
		lastUndoneCommand = lastCommand;
		
		return feedback;
	}
	
	public static String redo(String lastExecutedCommand) {
		String feedback;
		
		if (!lastExecutedCommand.equals("undo")) {
			return REDO_FAILED;
		}
		
		switch (lastUndoneCommand) {
		case "create":
			lastCreatedTask = Storage.store(lastCreatedTask.getTaskID(), lastCreatedTask);
			feedback = REDO_SUCCESS;
			break;
		case "delete":
			lastDeletedTask = Storage.delete(lastDeletedTask.getTaskID());
			feedback = REDO_SUCCESS;
			break;
		case "update":
			Storage.delete(lastUpdatedTask.getTaskID());
			Storage.store(lastUpdatedTask.getTaskID(), lastUpdatedTask);
			feedback = REDO_SUCCESS;
			break;
		default:
			feedback = REDO_FAILED;
		}

		lastRedoneCommand = lastUndoneCommand;
		
		return feedback;
	}
}
