package com.tobedone.logic;

import java.util.Vector;

import com.tobedone.taskitem.TaskItem;


/**
 * @author Tian Xing (A0117215R)
 * @version 
 * @since 01-09-2013
 * 
 *        This class define the data structure of Command.execute result  
 *        which contains the aimed taskItem list & execute feedback
 * 
 * 
 */
public class CommandExecuteResult {
	private Vector<TaskItem> aimTasks;
	private String feedback;
	
	// @author A0117215R
	public CommandExecuteResult(Vector<TaskItem> aimTasks, String feedback) {
		this.aimTasks = aimTasks;
		this.feedback = feedback;
	}

	// @author A0117215R
	public Vector<TaskItem> getAimTasks() {
		return aimTasks;
	}
	
	// @author A0117215R
	public String getFeedback() {
		return feedback;
	}	
}
