package com.tobedone.command;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;


public class UpdateCommand extends Command {

	private static int index = -1;
	Vector <TaskItem> aimTasks;
	private String newDescription = null;
	private Date newStartTime = null;
	private Date newEndTime = null;
	private int priority = -1;
	private Date deadline = null;
	public UpdateCommand (int id, String des, Date start, Date end, Date deadline, int prioirty){
		index = id;
		newDescription = des;
		newStartTime = start;
		newEndTime = end;
		this.deadline = deadline;
		this.priority = prioirty;
		isUndoable = true;
		aimTasks = new Vector<TaskItem>();
	}
	
	public void executeCommand() throws TaskNotExistException, IOException {
		TaskItem newTask = setParams();
		toDoService.updateTask(index, newTask);
	}
	
	public TaskItem setParams(){
		TaskItem task = null;
		TaskItem currentTask = toDoService.getAllTasks().get(index);
		if (currentTask instanceof FloatingTask) {
			if (newDescription!=null){
				currentTask.setDescription(newDescription);
			} 
			if (priority != -1) {
				currentTask.setPriority(priority);
			}
		} else if (currentTask instanceof DeadlinedTask) {
			if (newDescription!=null){
				currentTask.setDescription(newDescription);
			} 
			if (deadline != null){
				((DeadlinedTask) currentTask).setEndTime(deadline);
			}
			if (priority != -1) {
				currentTask.setPriority(priority);
			}
		} else if (currentTask instanceof TimedTask){
			if (newDescription!=null){
				currentTask.setDescription(newDescription);
			} 
			if (newEndTime != null){
				((TimedTask) currentTask).setEndTime(newEndTime);
			}
			if (priority != -1) {
				currentTask.setPriority(priority);
			}
			if (newStartTime != null){
				((TimedTask) currentTask).setStartTime(newStartTime);
			}	
		}
		task = currentTask;
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
