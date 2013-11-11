package com.tobedone.command;

import java.io.IOException;

import com.tobedone.command.utilities.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * 
 * @author A0118441M
 * @version 0.5
 * @since 11-11-2013
 * 
 *        This class handles the execution of the redo command.
 */

public class RedoCommand extends Command {
	private CommandHistory history;
	private Command lastUndone;

	public RedoCommand() {
		super();
		history = CommandHistory.getInstance();
	}

	/**
	 * Executes the redo command.
	 */
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_REDO_ACTION);

		boolean lastCommandIsUndo = history.getLastInvocation().equals(
				Constants.POP_OPERATION);
		if (lastCommandIsUndo) {
			lastUndone = history.popUndone();
			lastUndone.executeCommand();
			for (TaskItem task : lastUndone.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = lastUndone.getFeedback();
		} else {
			logger.debug(LogMessages.DEBUG_REDO_TWICE);
			for (TaskItem task : toDoService.getMatchingTasks()) {
				aimTasks.add(task);
			}
			feedback = Constants.MSG_REDO_TWICE_FAILED;
		}
	}

}
