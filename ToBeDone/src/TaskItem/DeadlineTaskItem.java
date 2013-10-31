package TaskItem;

import java.util.Calendar;
import java.util.Date;

public class DeadlineTaskItem extends TaskItem {

	private Date deadline;
	public DeadlineTaskItem(String description, int priority, Date deadline) {
		super(description, priority);
		this.deadline = deadline;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public void updateStatus() {
	Date currentDate = (Date) Calendar.getInstance().getTime();
	if (deadline.before(currentDate)) {
		status = Status.EXPIRED;
	}
}
	
	public String toStorageFormat(){
		String formatedstring;
		String deadlinestring = simpleDateFormat.format(deadline);
		
		formatedstring = super.toStorageFormat()+deadlinestring;
		return formatedstring;
	}
	
	public boolean equals(DeadlineTaskItem task) {
		boolean equalDeadline = this.deadline.equals(task.deadline);
		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = super.equals(task)&&equalDeadline&& equalPriority && equalStatus;
		
		return equalTask;
	}
}
