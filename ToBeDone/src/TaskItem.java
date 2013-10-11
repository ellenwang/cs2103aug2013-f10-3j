import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskItem {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM','HH:mm");

	private int taskID;
	private String description;
	private Date startTime;
	private Date endTime;
	private int priority;
	private Status status;

	static enum Status {
		FINISHED, UNFINISHED, EXPIRED
	};

	TaskItem() {

	}

	TaskItem(String description, Date startTime, Date endTime, int priority) {
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		status = Status.UNFINISHED;
	}

	TaskItem(String description, Date startTime, Date endTime, int priority,
			Status status) {
		this(description, startTime, endTime, priority);
		this.status = status;
	}

	public void setDescription(String taskDescription) {
		this.description = taskDescription;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
			status = Status.EXPIRED;
		}

	}

	public String toString() {
		String result = "";
		String formattedStartTime = formatDate(getStartTime());
		String formattedEndTime = formatDate(getEndTime());

		if (getStartTime() != null) {
			result = getDescription() + "\t starts from: " + formattedStartTime
					+ "\t ends at: " + formattedEndTime;
		} else if (getEndTime() != null) {
			result = getDescription() + "\t deadline: " + formattedEndTime;
		} else {
			result = getDescription();
		}
		return result;
	}

	private static String formatDate(Date date) {
		String formattedDate;
		if (date != null) {
			formattedDate = simpleDateFormat.format(date);
		} else {
			formattedDate = "";
		}

		return formattedDate;
	}
}
