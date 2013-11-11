package com.tobedone.command;

import java.io.IOException;

import com.tobedone.command.utilities.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class RedoCommand extends Command {

	private CommandHistory history;
	private Command lastUndone;

	// @author A0105682H
	public RedoCommand() {
		super();
		history = CommandHistory.getInstance();
	}

	// @author A0105682H
	@Override
	protected void executeCommand() throws TaskNotExistException, IOException {
		if (history.getLastInvocation().equals("pop")) {
			lastUndone = history.popUndone();
			lastUndone.executeCommand();
			for (TaskItem task : lastUndone.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = lastUndone.getFeedback();
		} else {
			for (TaskItem task : lastUndone.getAimTasks()) {
				aimTasks.add(task);
			}
			feedback = Constants.MSG_REDO_TWICE_FAILED;
		}
	}

}
