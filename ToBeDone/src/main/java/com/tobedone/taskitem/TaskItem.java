package com.tobedone.taskitem;

import com.tobedone.utilities.Constants;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import com.tobedone.utilities.Constants;


/**
 * @author A0105682H
 * @version 0.5
 * @date 04-11-2013
 * 
 *        This is a super class of task items.
 * 
 */

abstract public class TaskItem implements Cloneable {
	protected static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"HH:mm,dd/MM,yyyy");

	protected int taskID;
	protected String description;
	protected int priority;
	protected Status status;

	public static enum Status {
		FINISHED, UNFINISHED, EXPIRED
	};

	public static enum Priority {
		HIGH, MEDIUM, LOW
	};

	public TaskItem() {

	}

	public TaskItem(String description, int priority) {
		this.taskID = Constants.SERVICE_OPERATION_FAILED_ID;
		this.description = description;
		this.priority = priority;
		status = Status.UNFINISHED;
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

	public String getDescription() {
		return description;
	}

	public int getTaskID() {
		return taskID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

//	// @author A0105682H
//	private static int getEnumPri(TaskItem task) {
//		switch (task.getPriority()) {
//		case Constants.PRIORITY_HIGH:
//			return Priority.HIGH;
//		case Constants.PRIORITY_MEDIUM:
//			return Priority.MEDIUM;
//		case Constants.PRIORITY_LOW:
//			return Priority.LOW;
//		default:
//			return Priority.MEDIUM;
//		}
//	}

	/**
	 * Comparator for priority of two tasks object
	 */
	// @author A0105682H
	public static Comparator<TaskItem> TaskPriorityComparator = new Comparator<TaskItem>() {
		public int compare(TaskItem thisTask, TaskItem otherTask) {
			Integer thisTaskPri = thisTask.getPriority();
			Integer otherTaskPri = otherTask.getPriority();
			return thisTaskPri.compareTo(otherTaskPri);			
		}
	};
	
	public String toString() {
		String result = "";
		result = getDescription();
		return result;	
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TaskItem)) {
			return false;
		}
		TaskItem task = (TaskItem) obj;
		
		boolean equalDescription = this.description.equals(task.description);
		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = equalDescription && equalPriority && equalStatus;
		return equalTask;
	}
	
	public String formatDate(Date date) {
		String formattedDate;
		if (date != null) {
			formattedDate = simpleDateFormat.format(date);
			formattedDate = formattedDate.substring(0, formattedDate.length() - 4);
		} else {
			formattedDate = "";
		}
		return formattedDate;
	}
}
