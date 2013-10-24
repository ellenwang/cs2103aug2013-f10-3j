import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskItem implements Cloneable {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM,HH:mmyyyy");

	private int taskID;
	private String description;
	private Date startTime;
	private Date endTime;
	private int priority;
	private Status status;

	static enum Status {
		FINISHED, UNFINISHED, EXPIRED
	};

	public TaskItem() {

	}

	public TaskItem(String description, Date startTime, Date endTime,
			int priority) {
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		status = Status.UNFINISHED;
	}

	public TaskItem(String description, Date startTime, Date endTime,
			int priority, Status status) {
		this(description, startTime, endTime, priority);
		this.status = status;
	}

	public TaskItem(TaskItem task) {
		this(task.getDescription(), task.getStartTime(), task.getEndTime(),
				task.getPriority(), task.getStatus());
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
		if (endTime != null && endTime.before(currentDate)) {
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

	public boolean equals(TaskItem task) {
		boolean equalDescription = this.description.equals(task.description);
		boolean equalStartTime = this.startTime.equals(task.startTime);
		boolean equalEndTime = this.endTime.equals(task.endTime);
		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = equalDescription && equalStartTime && equalEndTime
				&& equalPriority && equalStatus;
		return equalTask;
	}

	private static String formatDate(Date date) {
		String formattedDate;
		if (date != null) {
			formattedDate = simpleDateFormat.format(date);
			// remove year
			formattedDate = formattedDate.substring(0, formattedDate.length() - 4);
		} else {
			formattedDate = "";
		}

		return formattedDate;
	}
}
