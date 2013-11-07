package com.tobedone.command;

import java.io.IOException;

import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;



public class ExitCommand extends Command {
	// @author A0105682H
	public ExitCommand() {
		super();
		isUndoable = false;
	}

	// @author A0105682H
	@Override
	protected void executeCommand() {
		logger.info(LogMessages.INFO_EXIT);
		try {
			toDoService.exit();
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);
		}
		this.feedback = Constants.MSG_EXIT;
		this.exitSystemStatus = true;
	}
}
