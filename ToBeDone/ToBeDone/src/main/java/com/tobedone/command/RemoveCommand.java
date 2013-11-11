//@author A0105682H
package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class extends Command class and handles the execute and undo
 *        operation of remove command.
 * 
 */
public class RemoveCommand extends Command {
	private int index;
	private Vector<TaskItem> matchingTasks;
	private TaskItem taskToDelete;

	//Constructors
	public RemoveCommand(int index) {
		super();
		this.index = index;
		this.matchingTasks = toDoService.getMatchingTasks();
		isUndoable = true;
		executionSuccessful = false;
	}


	@Override
	/**
	 * Removes the specified task from the task list and modifies feedback.
	 */
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_REMOVE);
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}
		if (index < 0 || index > matchingTasks.size()) {
			feedback = Constants.MSG_INVALID_INDEX;
		} else {
			taskToDelete = matchingTasks.get(index);
			boolean deleteSuccessful = toDoService.deleteTask(taskToDelete);
			if (deleteSuccessful) {
				feedback = String.format(Constants.MSG_DELETE_SUCCESSFUL, taskToDelete);
				aimTasks.remove(taskToDelete);
				executionSuccessful = true;
			} else {
				feedback = Constants.MSG_DELETE_FAILED;
			}
		}
	}


	@Override
	/**
	 * Undo the last remove task and add back the removed task item.
	 */
	public void undo() {
		logger.info(LogMessages.INFO_UNDO_ACTION);
		try {
			toDoService.createTask(taskToDelete);
			feedback = String.format(Constants.MSG_REMOVE_UNDO, taskToDelete);
			aimTasks.add(taskToDelete);
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);
			feedback = Constants.MSG_UNDO_FAILED;
		}
	}
}
