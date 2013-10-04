
public class TaskItem {
	private int taskID;
	private String taskDescription;
	private boolean valid;
	
	void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	String getTaskDescription() {
		return taskDescription;
	}
	
	int getTaskID() {
		return taskID;
	}
	
}
