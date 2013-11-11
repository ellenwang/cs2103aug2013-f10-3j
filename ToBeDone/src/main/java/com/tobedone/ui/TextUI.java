package com.tobedone.ui;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.logic.CommandExecutor;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class takes charge of TextUI of 2BeDone
 * 
 */
public class TextUI {
	private static String commandString;
	private static CommandExecuteResult commandExecuteResult;
	private static CommandExecutor executor;

	protected static void setCommandString(String command) {
		commandString = command;
	}

	// @author A0117215R
	/**
	 * execute the command string.
	 */
	protected static void executeCommands() throws Exception {
		if ((!commandString.equals(Constants.EXIT_TOBEDONE))) {
			try {
				executor = CommandExecutor.getInstance(commandString);
				commandExecuteResult = executor.getExecuteResult();
			} catch (Exception e) {
				throw e;
			}
		} else {
			System.exit(0);
		}
	}

	// @author A0117215R
	/**
	 * get input string from User
	 */
	protected static void readUserInput(String input) {
		commandString = input;
	}

	protected static CommandExecuteResult getCommandExecuteResult() {
		return commandExecuteResult;
	}

}
