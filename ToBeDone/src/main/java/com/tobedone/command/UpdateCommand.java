package com.tobedone.command;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;


public class UpdateCommand extends Command {

	private static int index = -1;
	private String newDescription = null;
	private Date newStartTime = null;
	private Date newEndTime = null;
	private int priority = 2;
	private Date deadline = null;
	public UpdateCommand (int id, String des, Date start, Date end, Date dealine, int prioirty){
		index = id;
		newDescription = des;
		newStartTime = start;
		newEndTime = end;
		this.deadline = deadline;
		this.priority = prioirty;
		isUndoable = true;
	}
	
	public void executeCommand() throws TaskNotExistException, IOException {
		TaskItem newTask = setParams();
		toDoService.updateTask(index, newTask);
	}
	
	public TaskItem setParams(){
		TaskItem task = null;
		
		return task;
	}
	public void undo(){
		TaskItem updatedTask;
		TaskItem oldTask;
		try {
			oldTask = toDoService.getLastUpdatedTask();
			updatedTask = toDoService.getLastCreatedTask();
			if (updatedTask != null) {
				toDoService.deleteTask(updatedTask);
				toDoService.createTask(oldTask);
			}
			feedback = String.format(Constants.MSG_UPDATE_SUCCESSFUL,
					oldTask, updatedTask);
		} catch (TaskNotExistException | IOException e) {
			logger.error(LogMessages.ERROR_UPDATE_UNDO_FAILED);
			feedback = Constants.MSG_UNDO_UPDATE_FAILED;
		}
	}

}
