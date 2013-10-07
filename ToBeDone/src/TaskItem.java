import java.util.Date;


public class TaskItem {
	private int taskID;
	private String description;
	private boolean valid;
	private Date startTime;
	private Date endTime;
	private int priority;
	
	TaskItem() {
		
	}
	
	TaskItem(String description, Date startTime, Date endTime, int priority) {
		description = this.description;
		startTime = this.startTime;
		endTime = this.endTime;
		priority = this.priority;
	}
	
	public void setDescription(String taskDescription) {
		this.description = taskDescription;
	}
	
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	String getDescription() {
		return description;
	}
	
	int getTaskID() {
		return taskID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}

