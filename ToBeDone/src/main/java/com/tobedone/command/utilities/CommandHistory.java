package com.tobedone.command.utilities;

import org.apache.log4j.Logger;

import java.util.Stack;

import com.tobedone.command.Command;
import com.tobedone.command.NotUndoableCommand;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;


/**
 * @author A0105682H
 * @version 0.5
 * @since 4-11-2013
 * 
 *        This singleton class handles all commands in command history for undo
 *        purpose. Data structure used: stack.
 * 
 */
public class CommandHistory {
	private static CommandHistory singleton = null;
	private static Stack<Command> commandlist;
	protected static Logger logger = Logger.getLogger(CommandHistory.class);

	// @author A0105682H
	private CommandHistory() {
		commandlist = new Stack<Command>();
	}

	// @author A0105682H
	public static CommandHistory getInstance() {
		if (singleton == null) {
			singleton = new CommandHistory();
		}
		return singleton;
	}

	/**
	 * Stack an object command
	 */
	// @author A0105682H
	public void push(Command cmd) {
		logger.info(LogMessages.INFO_PUSH_COMMAND);
		commandlist.push(cmd);
	}

	/**
	 * De-Stack an object command
	 */
	// @author A0105682H
	public Command pop() {
		logger.info(LogMessages.INFO_POP_COMMAND);
		Command lastestCommand = new NotUndoableCommand();
		return lastestCommand;
	}
}