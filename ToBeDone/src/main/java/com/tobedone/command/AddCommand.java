package com.tobedone.command;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import com.tobedone.command.utilities.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class AddCommand extends Command {

	private String description = null;
	private Date startTime = null;
	private Date endTime = null;
	private Date deadline = null;
	private int priority = 2;
	private Vector<TaskItem> allTasks;
	private Date currentTime = new Date();

	public AddCommand(String description, Date startTime, Date endTime,
			Date deadline, int priority) {
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deadline = deadline;
		if (priority != 2) {
			this.priority = priority;
		}
		this.allTasks = toDoService.getAllTasks();
		isUndoable = true;
	}

	@Override
	public void executeCommand() throws IOException {
		// TODO Auto-generated method stub
		logger.info(LogMessages.INFO_ADD);
		TaskItem newTask = getNewTaskWithParams();
		if (newTask == null) {
			logger.debug(LogMessages.DEBUG_NO_CONTENT);
			feedback = Constants.MSG_ERROR_NO_TASK_CONTENT;
		} else {
			try {
				boolean isAdded = toDoService.createTask(newTask);
				if (isAdded) {
					feedback = Constants.INFO_ADD;
					for (TaskItem task : toDoService.getAllTasks()) {
						aimTasks.add(task);
					}
				} else {
					feedback = Constants.MSG_ADDED_FAILED;
				}
			} catch (IOException e) {
				logger.error(LogMessages.ERROR_FILE);
				feedback = Constants.MSG_ADDED_FAILED;
			}
		}
	}

	@Override
	public void undo() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_CREATE_UNDO);
		try {
			logger.info(LogMessages.INFO_UNDO_ACTION);
			TaskItem createdTask = getNewTaskWithParams();
			if (toDoService.deleteTask(createdTask)) {
				feedback = Constants.MSG_DELETE_SUCCESSFUL;
				aimTasks.remove(createdTask);
			} else {
				feedback = Constants.EMPTY_STRING;
			}
		} catch (TaskNotExistException | IOException e) {
			logger.error(LogMessages.ERROR_ADD_UNDO_FAILED);
			feedback = Constants.MSG_UNDO_FAILED;
		}
	}

	public TaskItem getNewTaskWithParams() {
		TaskItem newTask = null;
		if (description == null) {
			newTask = null;
		} else if (deadline != null && startTime == null && endTime == null) {
			if (deadline.after(currentTime)) {
				newTask = new DeadlinedTask(description, deadline, priority);
			} else {
				newTask = null;
			}
		} else if (deadline == null && startTime != null && endTime != null) {
			if (endTime.after(currentTime) && endTime.after(startTime))
				newTask = new TimedTask(description, startTime, endTime,
						priority);
			else
				newTask = null;
		} else if (deadline == null && startTime == null && endTime == null) {
			newTask = new FloatingTask(description, priority);
		} else {
			newTask = null;
		}
		return newTask;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
