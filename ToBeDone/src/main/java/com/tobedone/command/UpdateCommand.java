package com.tobedone.command;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * 
 * @author A0118441M
 * @version 0.5
 * @since 11-11-2013
 * 
 *        This class handles the execute and undo operation of update command.
 */
public class UpdateCommand extends Command {
	private int index;
	private TaskItem oldTask;
	private TaskItem newTask;
	private Vector<TaskItem> matchingTasks;

	public UpdateCommand(int id, String des, Date start, Date end,
			Date deadline, int priority) {
		index = id;
		isUndoable = true;
		this.matchingTasks = toDoService.getMatchingTasks();
		oldTask = matchingTasks.get(index);
		newTask = getNewTask(oldTask, des, start, end, deadline, priority);
		executionSuccessful = false;
	}

	/**
	 * Executes the update command.
	 * 
	 */
	public void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UPDATE_TASK);
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}

		if (toDoService.getAllTasks().contains(newTask)) {
			logger.debug(LogMessages.DEBUG_UPDATE_TO_EXISTING_TASK);
			feedback = Constants.MSG_TASK_ALREADY_EXISTS;
		} else {
			TaskItem oldTask = toDoService.updateTask(index, newTask);
			aimTasks.remove(oldTask);
			aimTasks.add(newTask);

			feedback = Constants.MSG_UPDATE_SUCCESSFUL;
			executionSuccessful = true;
		}
	}

	/**
	 * Returns the description the task is to be updated to.
	 * 
	 * @param newDescription
	 *            description to update to or null if description is not updated
	 * @return description to update to
	 */
	public String getUpdatedDescription(String newDescription) {
		boolean descriptionIsUpdated = newDescription != null;
		if (descriptionIsUpdated) {
			return newDescription;
		} else {
			return oldTask.getDescription();
		}
	}

	/**
	 * Returns the priority the task is to be updated to.
	 * 
	 * @param newPriority
	 *            priority to update to or 0 if it is not to be updated
	 * @return priority to update to
	 */
	public int getUpdatedPriority(int newPriority) {
		boolean priorityIsUpdated = newPriority != 0;
		if (priorityIsUpdated) {
			return newPriority;
		} else {
			return oldTask.getPriority();
		}
	}

	/**
	 * Returns the updated task when the old task is a timed task.
	 * 
	 * @param oldTask
	 *            the task before the update
	 * @param newDescription
	 *            description to update to or null if it is not to be updated
	 * @param newStartTime
	 *            start time to update to or null if it is not to be updated
	 * @param newEndTime
	 *            end time to update to or null if it is not to be updated
	 * @param newDeadline
	 *            deadline to update to or null if it is not to be updated
	 * @param newPriority
	 *            priority to update to or 0 if it is not to be updated
	 * @return the updated task
	 */
	public TaskItem getNewTask(TimedTask oldTask, String newDescription,
			Date newStartTime, Date newEndTime, Date newDeadline,
			int newPriority) {
		TaskItem newTask;
		String description;
		int priority;
		Date startTime;
		Date endTime;

		description = getUpdatedDescription(newDescription);
		priority = getUpdatedPriority(newPriority);

		if (newStartTime != null) {
			startTime = newStartTime;
		} else {
			startTime = oldTask.getStartTime();
		}

		if (newEndTime != null) {
			endTime = newEndTime;
		} else {
			endTime = oldTask.getEndTime();
		}

		newTask = new TimedTask(description, startTime, endTime, priority);

		Status oldTaskStatus = oldTask.getStatus();
		newTask.setStatus(oldTaskStatus);
		return newTask;
	}

	/**
	 * Returns the updated task when the old parameter is a deadlined task.
	 * 
	 * @param oldTask
	 *            the task before the update
	 * @param newDescription
	 *            description to update to or null if it is not to be updated
	 * @param newStartTime
	 *            start time to update to or null if it is not to be updated
	 * @param newEndTime
	 *            end time to update to or null if it is not to be updated
	 * @param newDeadline
	 *            deadline to update to or null if it is not to be updated
	 * @param newPriority
	 *            priority to update to or 0 if it is not to be updated
	 * @return the updated task
	 */
	public TaskItem getNewTask(DeadlinedTask oldTask, String newDescription,
			Date newStartTime, Date newEndTime, Date newDeadline,
			int newPriority) {
		TaskItem newTask;
		String description;
		int priority;
		Date deadline;
		Date startTime;
		Date endTime;

		description = getUpdatedDescription(newDescription);
		priority = getUpdatedPriority(newPriority);

		if (newStartTime != null) {
			startTime = newStartTime;
			if (newEndTime != null) {
				endTime = newEndTime;
			} else {
				endTime = oldTask.getEndTime();
			}

			newTask = new TimedTask(description, startTime, endTime, priority);
		} else if (newDeadline != null) {
			deadline = newDeadline;
			newTask = new DeadlinedTask(description, deadline, priority);
		} else {
			deadline = oldTask.getEndTime();
			newTask = new DeadlinedTask(description, deadline, priority);
		}

		Status oldTaskStatus = oldTask.getStatus();
		newTask.setStatus(oldTaskStatus);
		return newTask;
	}

	/**
	 * Returns the updated task when the old task is a floating task.
	 * 
	 * @param oldTask
	 *            the task before the update
	 * @param newDescription
	 *            description to update to or null if it is not to be updated
	 * @param newStartTime
	 *            start time to update to or null if it is not to be updated
	 * @param newEndTime
	 *            end time to update to or null if it is not to be updated
	 * @param newDeadline
	 *            deadline to update to or null if it is not to be updated
	 * @param newPriority
	 *            priority to update to or 0 if it is not to be updated
	 * @return the updated task
	 */
	public TaskItem getNewTask(FloatingTask oldTask, String newDescription,
			Date newStartTime, Date newEndTime, Date newDeadline,
			int newPriority) {
		TaskItem newTask;
		String description;
		int priority;
		Date deadline;
		Date startTime;
		Date endTime;

		description = getUpdatedDescription(newDescription);
		priority = getUpdatedPriority(newPriority);

		if (newDeadline != null) {
			deadline = newDeadline;
			newTask = new DeadlinedTask(description, deadline, priority);
		} else if (newStartTime != null && newEndTime != null) {
			startTime = newStartTime;
			endTime = newEndTime;
			newTask = new TimedTask(description, startTime, endTime, priority);
		} else {
			newTask = new FloatingTask(description, priority);
		}

		Status oldTaskStatus = oldTask.getStatus();
		newTask.setStatus(oldTaskStatus);
		return newTask;
	}

	/**
	 * Returns the updated task.
	 * 
	 * @param oldTask
	 *            the task before the update
	 * @param newDescription
	 *            description to update to or null if it is not to be updated
	 * @param newStartTime
	 *            start time to update to or null if it is not to be updated
	 * @param newEndTime
	 *            end time to update to or null if it is not to be updated
	 * @param newDeadline
	 *            deadline to update to or null if it is not to be updated
	 * @param newPriority
	 *            priority to update to or 0 if it is not to be updated
	 * @return the updated task
	 */
	public TaskItem getNewTask(TaskItem oldTask, String newDescription,
			Date newStartTime, Date newEndTime, Date newDeadline,
			int newPriority) {
		if (oldTask instanceof TimedTask) {
			return getNewTask((TimedTask) oldTask, newDescription,
					newStartTime, newEndTime, newDeadline, newPriority);
		} else if (oldTask instanceof DeadlinedTask) {
			return getNewTask((DeadlinedTask) oldTask, newDescription,
					newStartTime, newEndTime, newDeadline, newPriority);
		} else {
			return getNewTask((FloatingTask) oldTask, newDescription,
					newStartTime, newEndTime, newDeadline, newPriority);
		}
	}

	/**
	 * Undoes the update command.
	 */
	public void undo() {
		try {
			logger.info(LogMessages.INFO_UPDATE_UNDO);
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
