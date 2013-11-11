//@author A0105682H
package com.tobedone.taskitem;

import java.util.Date;
import java.util.Comparator;

import com.tobedone.utils.Constants;

/**
 * @author A0105682H
 * @version 0.5
 * @date 04-11-2013
 * 
 *       This class inherits form TaskItem class and caters to timed tasks.
 * 
 */
public class TimedTask extends TaskItem {
	private Date startTime;
	private Date endTime;

	// Constructor
	public TimedTask(String description, Date startTime, Date endTime,
			int priority) {
		super(description, priority);
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Getters and setters
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

	/**
	 * Converts the information of a timed task into String.
	 */
	public String toString() {
		String result = Constants.EMPTY_STRING;
		String formattedStartTime = formatDate(getStartTime());
		String formattedEndTime = formatDate(getEndTime());
		result = getDescription() + "\n\tfrom: " + formattedStartTime
				+ "\n\tto:   " + formattedEndTime;
		return result;
	}

	/**
	 * Compares two timed tasks.
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TimedTask)) {
			return false;
		}

		TimedTask task = (TimedTask) obj;

		boolean equalDescription = this.description.equals(task.description);
		boolean equalStartTime;
		if (startTime == null) {
			equalStartTime = task.startTime == null;
		} else {
			equalStartTime = startTime.equals(task.startTime);
		}
		boolean equalEndTime;
		if (endTime == null) {
			equalEndTime = task.startTime == null;
		} else {
			equalEndTime = endTime.equals(task.endTime);
		}

		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = equalDescription && equalPriority && equalStatus
				&& equalStartTime && equalEndTime;
		return equalTask;
	}

	/**
	 * Comparator for Start Date of two tasks object
	 */
	public static Comparator<TimedTask> TaskDateComparator = new Comparator<TimedTask>() {
		public int compare(TimedTask thisTask, TimedTask otherTask) {
			Date thisTaskDate = thisTask.getStartTime();
			Date otherTaskDate = otherTask.getStartTime();
			if (thisTaskDate == null && otherTaskDate == null)
				return com.tobedone.utils.Constants.ZERO;
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
