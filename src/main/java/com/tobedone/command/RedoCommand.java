package com.tobedone.command;

import java.io.IOException;

import com.tobedone.command.utilities.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
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
		lastUndone = history.popUndone();
		lastUndone.executeCommand();
		for (TaskItem task : lastUndone.getAimTasks()) {
			aimTasks.add(task);
		}
		feedback = lastUndone.getFeedback();
	}

}
