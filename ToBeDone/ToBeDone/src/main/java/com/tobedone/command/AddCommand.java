//@author A0105682H
package com.tobedone.command;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class extends Command class and handles the execute and undo
 *        operation of add command.
 * 
 */
public class AddCommand extends Command {

	private String description = null;
	private Date currentTime = new Date();
	private Date startTime = null;
	private Date endTime = null;
	private Date deadline = null;
	private int priority = 2; // the default priority is medium
	private Vector<TaskItem> allTasks;

	// Constructor
	public AddCommand(String description, Date startTime, Date endTime,
			Date deadline, int priority) {
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deadline = deadline;
		this.priority = priority;
		this.allTasks = toDoService.getAllTasks();
		isUndoable = true;
		executionSuccessful = false;
	}

	@Override
	/**
	 * Adds the new task it the new task generated is valid and modifies feedback.
	 */
	public void executeCommand() throws IOException {
		logger.info(LogMessages.INFO_ADD);
		
		TaskItem newTask = this.getNewTaskWithParams();
		aimTasks.clear();
		for (TaskItem task : toDoService.getAllTasks()) {
			aimTasks.add(task);
		}
		if (newTask == null) {
			logger.debug(LogMessages.DEBUG_NO_CONTENT);
		} else if (allTasks.contains(newTask)) {
			feedback = Constants.MSG_TASK_ALREADY_EXISTS;
		} else {
			try {
				boolean isAdded = toDoService.createTask(newTask);
				if (isAdded) {
					feedback = String.format(Constants.MSG_ADD_SUCCESSFUL,
							newTask);
					aimTasks.add(newTask);
					executionSuccessful = true;
				} else {
					feedback = Constants.MSG_ADD_FAILED;
				}
			} catch (IOException e) {
				logger.error(LogMessages.ERROR_FILE);
				feedback = Constants.MSG_ADD_FAILED;
			}
		}
	}

	@Override
	/**
	 * Deletes the last added task to undo the last add command.
	 */
	public void undo() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_ADD_UNDO);

		try {
			logger.info(LogMessages.INFO_UNDO_ACTION);
			TaskItem createdTask = this.getNewTaskWithParams();
			if (toDoService.deleteTask(createdTask)) {
				feedback = String.format(Constants.MSG_DELETE_SUCCESSFUL,
						createdTask);
				aimTasks.remove(createdTask);
			} else {
				feedback = Constants.EMPTY_STRING;
			}
		} catch (TaskNotExistException | IOException e) {
			logger.error(LogMessages.ERROR_ADD_UNDO_FAILED);
			feedback = Constants.MSG_UNDO_ADD_FAILED;
		}
	}

	/**
	 * Processes the passed in parameters to add method and generate a new task item with these parameters.
	 * @return
	 * 			The new task item to be added with parameters specified.
	 */
	public TaskItem getNewTaskWithParams() {
		TaskItem newTask = null;
		if (description == null) {
			newTask = null;
		} else if (deadline != null && startTime == null && endTime == null) {

			if (deadline.after(currentTime)) {
				newTask = new DeadlinedTask(description, deadline, priority);
			} else {
				newTask = null;
				feedback = Constants.MSG_INVALID_DEADLINE;
			}
		} else if (deadline == null && startTime != null && endTime != null) {
			if (endTime.after(currentTime) && endTime.after(startTime))
				newTask = new TimedTask(description, startTime, endTime,
						priority);
			else
				newTask = null;
			if (endTime.before(currentTime)) {
				feedback = Constants.MSG_INVALID_DEADLINE;
			} else if (endTime.before(startTime)) {
				feedback = Constants.MSG_INVALID_TIMEPERIOD;
			}
		} else if (deadline == null && startTime == null && endTime == null) {
			newTask = new FloatingTask(description, priority);
		} else {
			newTask = null;
			feedback = Constants.MSG_ADD_FAILED;
		}
		return newTask;
	}
}
