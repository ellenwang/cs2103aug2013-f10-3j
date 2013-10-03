
public class TaskItem {
	private int taskID;
	private String taskDescription;
	
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
