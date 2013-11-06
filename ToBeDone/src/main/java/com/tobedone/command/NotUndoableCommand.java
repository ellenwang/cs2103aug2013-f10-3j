package com.tobedone.command;

import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;

public class NotUndoableCommand extends Command{
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
