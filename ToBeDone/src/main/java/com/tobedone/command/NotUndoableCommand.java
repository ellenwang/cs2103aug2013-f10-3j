package com.tobedone.command;

import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;


public class NotUndoableCommand extends Command {
	/**
	 * @author A0087510J
	 * @version 0.5
	 * @since 6-11-2012
	 * 
	 *        This class handles feedback when undo action is unavailable.
	 * 
	 */

	// @author A0087510J
	@Override
	protected void executeCommand() {
		// do nothing
	}

	// @author A0087510J
	@Override
	public void undo() {
		logger.debug(LogMessages.DEBUG_UNDO_NOTHING);
		feedback = Constants.MSG_NO_UNDO;
	}
}
