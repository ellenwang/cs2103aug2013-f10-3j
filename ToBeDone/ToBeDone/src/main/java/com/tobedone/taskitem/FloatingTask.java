package com.tobedone.taskitem;



/**
 * @author A0105682H
 * @version 0.5
 * @date 04-11-2013
 * 
 *        This is a super class of task items.
 * 
 */
//@A0105682H
public class FloatingTask extends TaskItem {
	public FloatingTask(String description, int priority) {
		super(description, priority);
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof FloatingTask)) {
			return false;
		}
		
		FloatingTask task = (FloatingTask) obj;

		boolean equalDescription = this.description.equals(task.description);
		boolean equalPriority = this.priority == task.priority;
		boolean equalStatus = this.status.equals(task.status);
		boolean equalTask = equalDescription && equalPriority && equalStatus;
		return equalTask;
	}
}
