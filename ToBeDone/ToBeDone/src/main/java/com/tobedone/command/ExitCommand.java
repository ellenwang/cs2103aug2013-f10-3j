//@author A0105682H
package com.tobedone.command;

import java.io.IOException;

import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class inherits from the Command class and caters for the exit
 *        command.
 * 
 */

public class ExitCommand extends Command {
	// Constructors
	public ExitCommand() {
		super();
		isUndoable = false;
	}

	@Override
	/**
	 * Exits from the system.
	 */
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
