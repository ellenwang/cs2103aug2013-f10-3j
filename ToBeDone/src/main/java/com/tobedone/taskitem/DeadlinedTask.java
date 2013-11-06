package com.tobedone.taskitem;
import com.tobedone.*;
import java.util.Date;
import java.util.Comparator;

import com.tobedone.utilities.Constants;

public class DeadlinedTask extends TaskItem {
private Date endTime;
	
	public DeadlinedTask (String description, Date endTime2, int priority) {
		super(description, priority);
		this.endTime = endTime2;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	public String toString() {
		String result = "";
		String formattedEndTime = formatDate(getEndTime());
		result = getDescription() + "\n\tdeadline: " + formattedEndTime;	
		return result;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TimedTask)) {
			return false;
		}
		TimedTask task = (TimedTask) obj;
		
		boolean equalDescription = this.description.equals(task.description);
		boolean equalEndTime ;
		if(this.endTime == task.getEndTime()){
			equalEndTime=true;
		}else{
			equalEndTime = false;
		}
		
		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = equalDescription && equalPriority && equalStatus && equalEndTime;
		return equalTask;
	}
	/**
	 * Comparator for End Date of two tasks object
	 */
	// @author A0105682H
	public static Comparator<DeadlinedTask> TaskDateComparator = new Comparator<DeadlinedTask>() {
		public int compare(DeadlinedTask thisTask, DeadlinedTask otherTask) {
			Date thisTaskDate = thisTask.getEndTime();
			Date otherTaskDate = otherTask.getEndTime();
			if (thisTaskDate == null && otherTaskDate == null)
				return com.tobedone.utilities.Constants.ZERO;
			else if (thisTaskDate != null && otherTaskDate == null) {
				return Constants.SERVICE_OPERATION_FAILED_ID;
			} else if (thisTaskDate == null && otherTaskDate != null) {
				return Constants.ONE;
			} else {
				return thisTaskDate.compareTo(otherTaskDate);
			}
		}
	};
}
