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
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class UpdateCommand extends Command {
	private static int index = -1;
	private String newDescription = null;
	private Date newStartTime = null;
	private Date newEndTime = null;
	private int newPriority = -1;
	private Date newDeadline = null;
	private TaskItem oldTask;
	private TaskItem newTask;
	private Vector<TaskItem> matchingTasks;

	public UpdateCommand(int id, String des, Date start, Date end,
			Date deadline, int priority) {
		index = id;
		newDescription = des;
		newStartTime = start;
		newEndTime = end;
		this.newDeadline = deadline;
		this.newPriority = priority;
		isUndoable = true;
		this.matchingTasks = toDoService.getMatchingTasks();
		oldTask = matchingTasks.get(index);
		executionSuccessful = false;
	}

	public void executeCommand() throws TaskNotExistException, IOException {
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}

		newTask = setParams();
		if (toDoService.getAllTasks().contains(newTask)) {
			feedback = Constants.MSG_TASK_ALREADY_EXISTS;
		} else {
			TaskItem oldTask = toDoService.updateTask(index, newTask);
			aimTasks.remove(oldTask);
			aimTasks.add(newTask);

			feedback = Constants.MSG_UPDATE_SUCCESSFUL;
			executionSuccessful = true;
		}
	}

	public TaskItem setParams() {
		TaskItem newTask;
		String description;
		int priority;
		Date deadline;
		Date startTime;
		Date endTime;

		if (newDescription != null) {
			description = newDescription;
		} else {
			description = oldTask.getDescription();
		}

		if (newPriority != 0) {
			priority = newPriority;
		} else {
			priority = oldTask.getPriority();
		}

		if (oldTask instanceof TimedTask) {
			if (newStartTime != null) {
				startTime = newStartTime;
			} else {
				startTime = ((TimedTask) oldTask).getStartTime();
			}

			if (newEndTime != null) {
				endTime = newEndTime;
			} else {
				endTime = ((TimedTask) oldTask).getEndTime();
			}

			newTask = new TimedTask(description, startTime, endTime, priority);
		} else if (oldTask instanceof DeadlinedTask) {
			if (newStartTime != null) {
				startTime = newStartTime;
				if (newEndTime != null) {
					endTime = newEndTime;
				} else {
					endTime = ((DeadlinedTask) oldTask).getEndTime();
				}

				newTask = new TimedTask(description, startTime, endTime,
						priority);
			} else if (newDeadline != null) {
				deadline = newDeadline;
				newTask = new DeadlinedTask(description, deadline, priority);
			} else {
				deadline = ((DeadlinedTask) oldTask).getEndTime();
				newTask = new DeadlinedTask(description, deadline, priority);
			}
		} else {
			if (newDeadline != null) {
				deadline = newDeadline;
				newTask = new DeadlinedTask(description, deadline, priority);
			} else if (newStartTime != null && newEndTime != null) {
				startTime = newStartTime;
				endTime = newEndTime;
				newTask = new TimedTask(description, startTime, endTime,
						priority);
			} else {
				newTask = new FloatingTask(description, priority);
			}
		}

		Status oldTaskStatus = oldTask.getStatus();
		newTask.setStatus(oldTaskStatus);

		return newTask;
	}

	public void undo() {
		try {
			aimTasks.add(oldTask);
			aimTasks.remove(newTask);
			toDoService.deleteTask(newTask);
			toDoService.createTask(oldTask);
			feedback = Constants.MSG_UPDATE_SUCCESSFUL;
		} catch (TaskNotExistException | IOException e) {
			logger.error(LogMessages.ERROR_UPDATE_UNDO_FAILED);
			feedback = Constants.MSG_UNDO_UPDATE_FAILED;
		}
	}
}
