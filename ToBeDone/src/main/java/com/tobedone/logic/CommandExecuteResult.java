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
	
	public CommandExecuteResult(Vector<TaskItem> aimTasks, String feedback) {
		this.aimTasks = aimTasks;
		this.feedback = feedback;
	}

	public Vector<TaskItem> getAimTasks() {
		return aimTasks;
	}
	
	public String getFeedback() {
		return feedback;
	}	
}
