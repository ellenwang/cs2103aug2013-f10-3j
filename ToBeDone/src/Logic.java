import java.util.Date;
import java.util.ArrayList;
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
	private static final String UNDO_SUCCESS = "The data has been recovered.";
	private static final String UNDO_FAILED = "Recover failed";
	private static final String UNDO_COMMAND_UNDOABLE = "Can not undo %1$s command";
	private static final String REDO_SUCCESS = "Redo succeed";
	private static final String REDO_FAILED = "Redo failed";
	private static Vector<TaskItem> aimTasks;

	private static Storage dataBase=new Storage("database.txt");
	static enum STATUS {
		finished, unfinished, expired
	};

	// variables used for undo
	private static int lastCreatedTaskID;
	private static TaskItem lastDeletedTask;
	private static TaskItem lastUpdatedTask;

	static enum LAST_COMMAND {
		create, delete, update, search, view, undo
	};

	static enum STATUS {
		finished, unfinished, expired
	};

	static String createTask(String description, Date startTime, Date endTime,
			int priority) {
		try {

			dataBase.store(createNewOne);
					endTime, priority);
			lastCreatedTaskID = Storage.store(createNewOne);
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

	/**static String deleteTask(int index){
		TaskItem toDelete;
		int TaskID=indexToTaskID(index);
		try{
			toDelete=Storage.retrieve(TaskID);
		}catch(Exception e){
			return INVALID_ITEM;
		}
		Storage.remove(toDelete);
		return DELETE_SUCCESS_MESSAGE;
	}**/
	
	static String updateTask(int index, String description, Date startTime, Date endTime, int priority ){
		TaskItem toUpdate;
		int TaskID = indexToTaskID(index);
		try {
			toUpdate = Storage.retrieve(TaskID);
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
		TaskItem temp=aimTasks[index];		
		return temp.getTaskID();
	}


	
	static String viewAll() {
		String result = "";
		Vector<TaskItem> list = Storage.retrieveAll();
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i).toString();
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


	public static String undo(String lastComType) {
		// TODO Auto-generated method stub
		return null;
	}


	public static String redo(String lastCommandString) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String undo(String lastComType) {
		switch (lastComType) {
		case "create":
			Storage.delete(lastCreatedTaskID);
			return UNDO_SUCCESS;
		case "delete":
			Storage.store(lastDeletedTask.getTaskID(), lastDeletedTask);
			return UNDO_SUCCESS;
		case "update":
			Storage.delete(lastUpdatedTask.getTaskID());
			Storage.store(lastUpdatedTask.getTaskID(), lastUpdatedTask);
			return UNDO_SUCCESS;
		default:
			return UNDO_FAILED;
		}
	}

	public static String redo(String lastCommandString) {
		// TODO Auto-generated method stub
		return null;
	}

}
