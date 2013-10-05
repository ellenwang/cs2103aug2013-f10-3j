package skeleton;

public class Logic {
	private static final String CREATED_FAIL_MESSAGE = "Creating new task failed.";
	private static final String MARKED_AS_DONE="The task is marked as done.";
	private static final String MARKED_AS_UNDONE="The task is marked as undone.";
	private static final String CREATED_SUCCESS_MESSAGE = "New task has been created.";
	private static final String DELETE_SUCCESS_MESSAGE="The task is deleted successfully.";
	private static final String UPDATE_SUCCESS_MESSAGE="The task is updated successfully.";
	private static final String INVALID_ITEM="Item not found.";
	
	static String createTask(String description, String startTime, String endTime, String priority){
		try{
			TaskItem createNewOne=new TaskItem(description, startTime, endTime, priority);
			Storage.save(createNewOne);
		}catch(Exception e){
			return CREATED_FAIL_MESSAGE;
		}
		return CREATED_SUCCESS_MESSAGE;
	}
	
	static String markTask(int index){
		TaskItem toMark;
		int TaskID=indexToTaskID(index);
		try{
			toMark=Storage.retrieve(TaskID);
		}catch(Exception e){
			return INVALID_ITEM;
		}
		toMark.setStatus(true);
		return MARKED_AS_DONE; 
	}
	
	static String unmarkTask(int index){
		TaskItem toUnMark;
		int TaskID=indexToTaskID(index);
		try{
			toUnMark=Storage.retrive(TaskID);
		}catch(Exception e){
			return INVALID_ITEM;
		}
		toUnMark.setStatus(false);
		return MARKED_AS_UNDONE;
	}
	
	static String deleteTask(int index){
		TaskItem toDelete;
		int TaskID=indexToTaskID(index);
		try{
			toDelete=Storage.retrive(TaskID);
		}catch(Exception e){
			return INVALID_ITEM;
		}
		Storage.remove(toDelete);
		return DELETE_SUCCESS_MESSAGE;
	}
	
	static String updateTask(int index, String description, String startTime, String endTime, String priority ){
		TaskItem toUpdate;
		int TaskID=indexToTaskID(index);
		try{
			toUpdate=Storage.retrive(TaskID);
		}catch(Exception e){
			return INVALID_ITEM;
		}
		if(description!=null){
			toUpdate.setTaskDescription(description);
		}
		if(startTime!=null){
			toUpdate.setStartTime(startTime);
		}
		if(endTime!=null){
			toUpdate.setEndTime(endTime);
		}
		if(priority!=null){
			toUpdate.setPriority(priority);
		}
		return UPDATE_SUCCESS_MESSAGE;
	}
	
	static TaskItem getItemByIndex(int index){
		TaskItem temp=TobeDoneUI.getItem(index);		
		return temp.getID();
	}
}
