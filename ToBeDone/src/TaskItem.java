import java.util.Date;


public class TaskItem {
	private int taskID;
	private String description;
	private boolean valid;
	private Date startTime=null;
	private Date endTime=null;
	private int priority = -1;
	
	
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
	public String toString(){
		String result = "";
		if (getStartTime()!=null) {
			result = "The task: "+getDescription()+"\t starts from: "+getStartTime()+"\t ends at: "+getEndTime()
				+"\t priority: "+getPriority();
		} else {
			result = "The task: "+getDescription()+"\t deadline: "+getEndTime()
					+"\t priority: "+getPriority();
		}
		return result;
	}
}

