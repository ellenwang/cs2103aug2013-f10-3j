
public class Storage {
	private static final String CREATED_MESSAGE = "Now task is created";
	static void save(String taskInfo){
		TaskItem taskItem =new TaskItem();
		taskItem.setTaskDescription("hello");
		taskItem.setTaskID(1);
		
		Logic.createDone(CREATED_MESSAGE);
	}
}
