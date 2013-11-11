//@author A0105682H
package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class inherits from the Command class and caters for the clear
 *        command.
 * 
 */

public class ClearCommand extends Command {

	private Vector<TaskItem> originalList;

	// Constructors
	public ClearCommand() {
		super();
		originalList = new Vector<TaskItem>();
		isUndoable = true;
		executionSuccessful = false;
	}

	@Override
	/**
	 * Removes all the tasks in the list of all tasks.
	 */
	public void executeCommand() {
		try {
			logger.info(LogMessages.INFO_CLEAR);

			originalList.clear();
			for (TaskItem task : toDoService.getAllTasks()) {
				originalList.add(task);
			}
			toDoService.clear();
			feedback = Constants.MSG_CLEAR;
			executionSuccessful = true;
			aimTasks = new Vector<TaskItem>();
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);

			feedback = Constants.MSG_CLEAR_FAILED;
		}
	}

	@Override
	/**
	 * Undo the clear command by adding back the original list of tasks.
	 */
	public void undo() {
		try {
			for (TaskItem task : originalList) {
				toDoService.createTask(task);
				feedback = Constants.MSG_CLEAR_UNDO;
				aimTasks = originalList;
			}
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);

			feedback = Constants.MSG_UNDO_CLEAR_FAILED;
		}
	}
}
