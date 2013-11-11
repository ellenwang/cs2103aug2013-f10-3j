//@author A0105682H
package com.tobedone.command.utilities;

import java.util.Stack;
import org.apache.log4j.Logger;
import com.tobedone.command.Command;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 4-10-2013
 * 
 *        This singleton class handles all commands in command history for undo
 *        and redo purpose. Data structure used: Stack.
 * 
 */

public class CommandHistory {

	protected static Logger logger = Logger.getLogger(CommandHistory.class);

	private static CommandHistory singleton = null;
	private static Stack<Command> commandList;
	private static Stack<Command> undoneList;
	private String lastInvocation;

	// Constructor
	private CommandHistory() {
		commandList = new Stack<Command>();
		undoneList = new Stack<Command>();
	}

	/**
	 * Gets an instance of the singleton CommandHistory object.
	 * 
	 * @return singleton instance
	 */
	public static CommandHistory getInstance() {
		if (singleton == null) {
			singleton = new CommandHistory();
		}
		return singleton;
	}

	/**
	 * Push the command call into the stack recording command history.
	 * 
	 * @param cmd
	 *            The command to be pushed.
	 */
	public void push(Command cmd) {
		logger.info(LogMessages.INFO_PUSH_COMMAND);

		commandList.push(cmd);
		this.setLastInvocation(Constants.PUSH_OPERATION);
	}

	/**
	 * Pop out the command from the command history stack for undo operation.
	 * 
	 * @return The latest command pushed into the stack.
	 */
	public Command pop() {
		logger.info(LogMessages.INFO_POP_COMMAND);

		Command lastCommand = commandList.pop();
		undoneList.push(lastCommand);
		this.setLastInvocation(Constants.POP_OPERATION);
		return lastCommand;
	}

	/**
	 * Pop out the command from the undo command stack for redo operation.
	 * 
	 * @return The latest undone command pushed into the stack.
	 */
	public Command popUndone() {
		logger.info(LogMessages.INFO_POP_UNDO_COMMAND);

		Command lastUndone = undoneList.pop();
		commandList.push(lastUndone);
		this.setLastInvocation(Constants.POP_UNDONE_OPERATION);
		return lastUndone;
	}

	/**
	 * Shows the last type of operation, helping to distinguish which type of
	 * command was manipulated.
	 * 
	 * @return The String that records the last invocation.
	 */
	public String getLastInvocation() {
		return lastInvocation;
	}

	/**
	 * Set the value of lastInvocation.
	 * 
	 * @param lastInvocation
	 *            The value of lastInvocation to update the variable.
	 */
	public void setLastInvocation(String lastInvocation) {
		this.lastInvocation = lastInvocation;
	}

}