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
 *        This class handles the execution of the undo command.
 */
public class UndoCommand extends Command {
	private CommandHistory history;
	private Command lastCommand;

	public UndoCommand() {
		super();
		history = CommandHistory.getInstance();
	}

	/**
	 * Executes the undo command.
	 * 
	 */
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UNDO_ACTION);

		boolean lastCommandIsUndo = history.getLastInvocation().equals("pop");
		if (!lastCommandIsUndo) {
			lastCommand = history.pop();
			lastCommand.undo();

			for (TaskItem task : lastCommand.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = lastCommand.getFeedback();
		} else {
			logger.debug(LogMessages.DEBUG_UNDO_TWICE);
			for (TaskItem task : toDoService.getMatchingTasks()) {
				aimTasks.add(task);
			}
			feedback = Constants.MSG_UNDO_TWICE_FAILED;
		}
	}
}
