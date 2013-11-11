package com.tobedone.command;

import java.io.IOException;

import com.tobedone.command.utils.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class UndoCommand extends Command {

	private CommandHistory history;
	private Command lastCommand;

	// @author A0105682H
	public UndoCommand() {
		super();
		history = CommandHistory.getInstance();
	}

	// @author A0105682H
	@Override
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UNDO_ACTION);
		if (!history.getLastInvocation().equals("pop")) {
			lastCommand = history.pop();
			lastCommand.undo();
		
			for (TaskItem task : lastCommand.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = lastCommand.getFeedback();
		} else {
			for (TaskItem task : lastCommand.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = Constants.MSG_UNDO_TWICE_FAILED;
		}
	}

}