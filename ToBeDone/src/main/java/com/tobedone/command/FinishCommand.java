//@author A0105682H
package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class extends Command class and handles the execute and undo
 *        operation of finish command.
 * 
 */

public class FinishCommand extends Command {
	private TaskItem finishedTask;
	private Vector<TaskItem> matchingTasks;
	private int index;

	// Constructor
	public FinishCommand(int index) {
		this.index = index;
		this.matchingTasks = toDoService.getMatchingTasks();
		isUndoable = true;
		executionSuccessful = false;
	}

	@Override
	/**
	 * Execute the finish command calls the completeTask method in logic.
	 */
	public void executeCommand() throws IOException, TaskNotExistException {
		logger.info(LogMessages.INFO_FINISH);
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}
		if (index < 0 || index > matchingTasks.size()) {
			feedback = Constants.MSG_INVALID_INDEX;
		} else {
			finishedTask = matchingTasks.get(index);
			if (!finishedTask.getStatus().equals(Status.FINISHED)) {
				toDoService.deleteTask(finishedTask);
				finishedTask.setStatus(Status.FINISHED);
				toDoService.createTask(finishedTask);
				feedback = String.format(Constants.MSG_FINISH_SUCCESSFUL,
						finishedTask);
				executionSuccessful = true;
			} else {
				feedback = String.format(Constants.MSG_ALREADY_FINISHED,
						finishedTask);
			}
		}
	}

	@Override
	/**
	 * Undo the finish command
	 */
	public void undo() {
		try {
			toDoService.deleteTask(finishedTask);
			finishedTask.setStatus(Status.UNFINISHED);
			toDoService.createTask(finishedTask);
			feedback = Constants.MSG_UNDO_SUCCESSFUL;
		} catch (TaskNotExistException e) {
			logger.error(LogMessages.ERROR_TASK_NOTFOUND);
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
		}
	}
}
