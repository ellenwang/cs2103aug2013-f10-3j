package TaskItem;

import java.util.Calendar;
import java.util.Date;

public class TimedTaskItem extends TaskItem{
	
	private Date startTime;
	private Date endTime;
	
	
	public TimedTaskItem(String description, int priority, Date startTime, Date endTime) {
		super(description, priority);
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	//getter and setter
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
	
	public void updateStatus() {
		Date currentDate = (Date) Calendar.getInstance().getTime();
		if (endTime.before(currentDate)) {
			status = Status.EXPIRED;
		}
	}
	
	public String toStorageFormat(){
		String formatedstring;
		String startTimeString = simpleDateFormat.format(startTime);
		String endTimeString = simpleDateFormat.format(endTime);
		
		formatedstring = super.toStorageFormat()+startTimeString+endTimeString;
		
		return formatedstring;
	}
	
	public boolean equals(TimedTaskItem task) {
		boolean equalStartTime = this.startTime.equals(task.startTime);
		boolean equalEndTime = this.endTime.equals(task.endTime);
		boolean equalTask = super.equals(task) && equalStartTime && equalEndTime;
		return equalTask;
	}
}
