import java.util.Calendar;
import java.util.Date;

public class TaskItem {
	private int taskID;
	private String description;
	private Date startTime;
	private Date endTime;
	private int priority;
	private STATUS status;

	static enum STATUS {
		finished, unfinished, expired
	};

	TaskItem() {

	}

	TaskItem(String description, Date startTime, Date endTime, int priority) {
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		status = STATUS.unfinished;
	}

	public void setDescription(String taskDescription) {
		this.description = taskDescription;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getStatus() {
		switch (status) {
		case unfinished:
			return 1;
		case finished:
			return 2;
		case expired:
			return 3;
		default:
			return -1;
		}
	}

	public void setStatus(int p) {
		switch (p) {
		case 1:
			status = STATUS.unfinished;
			break;
		case 2:
			status = STATUS.finished;
			break;
		case 3:
			status = STATUS.expired;
			break;
		}
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

	public void updateStatus() {
		Date currentDate = (Date) Calendar.getInstance().getTime();
		if (endTime.before(currentDate)) {
			this.setStatus(3);
		}

	}

	public String toString() {
		String result = "";
		if (getStartTime() != null) {
			result = getDescription() + "\t starts from: " + getStartTime()
					+ "\t ends at: " + getEndTime();
		} else if (getEndTime() != null) {
			result = getDescription() + "\t deadline: " + getEndTime();
		} else {
			result = getDescription();
		}
		return result;
	}
}
