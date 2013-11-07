package com.tobedone.taskitem;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.tobedone.utils.Constants;


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
	
	/**
	 * Comparator TaskItem
	 */
	public static Comparator<TaskItem> TaskItemComparator = new Comparator<TaskItem>() {
		public int compare(TaskItem thisTask, TaskItem otherTask) {
			Status thisStatus = thisTask.getStatus();
			Status otherStatus = otherTask.getStatus();
			int result = thisStatus.compareTo(otherStatus);
			if (result == 0) {
				Integer thisPriority = thisTask.getPriority();
				Integer otherPriority = otherTask.getPriority();
				result = thisPriority.compareTo(otherPriority);
				if (result == 0) {
					result = compareEndTimes(thisTask, otherTask);
					if (result == 0) {
						String thisDescription = thisTask.getDescription();
						String otherDescription = otherTask.getDescription();
						result = thisDescription.compareTo(otherDescription);
					}
				}
			}
			
			return result;
		}
	};
	
	/**
	 * Compare end times of two tasks.
	 */
	private static int compareEndTimes(TaskItem task1, TaskItem task2) {
		if (task1 instanceof FloatingTask) {
			if (task2 instanceof FloatingTask) {
				return 0;
			} else {
				return -1;
			}
		} else if (task1 instanceof DeadlinedTask) {
			if (task2 instanceof FloatingTask) {
				return 1;
			} else if (task2 instanceof DeadlinedTask) {
				Date endTime1 = ((DeadlinedTask) task1).getEndTime();
				Date endTime2 = ((DeadlinedTask) task2).getEndTime();
				return endTime2.compareTo(endTime1);
			} else {
				Date endTime1 = ((DeadlinedTask) task1).getEndTime();
				Date endTime2 = ((TimedTask) task2).getEndTime();
				return endTime2.compareTo(endTime1);
			}
		} else {
			if (task2 instanceof FloatingTask) {
				return 1;
			} else if (task2 instanceof DeadlinedTask) {
				Date endTime1 = ((TimedTask) task1).getEndTime();
				Date endTime2 = ((DeadlinedTask) task2).getEndTime();
				return endTime2.compareTo(endTime1);
			} else {
				Date endTime1 = ((TimedTask) task1).getEndTime();
				Date endTime2 = ((TimedTask) task2).getEndTime();
				return endTime2.compareTo(endTime1);
			}
		}
	}
	
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
