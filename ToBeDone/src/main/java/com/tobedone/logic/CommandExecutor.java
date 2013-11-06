package com.tobedone.logic;

import com.tobedone.command.Command;
import com.tobedone.parser.Parser;


/**
 * @author Tian Xing (A0117215R)
 * @version 
 * @since 01-09-2013
 * 
 *        This class will connect to Parser and other component,
 *        and the UI component will only connect to it.
 *        which implements "Law of Demeter" principle and reduces coupling 
 * 
 * 
 */
public class CommandExecutor {
	private static CommandExecutor theOneCommandExecutor;
	private String cmdString;
	private CommandExecuteResult result;
	
	private CommandExecutor(String cmdString) {
		this.cmdString = cmdString;
	}
	
	private void executeCommandString(String cmdString) throws Exception{
		Command command;
		command = Parser.getInstance().parseCommand(cmdString);
		this.result = command.execute();
	}
	
	public CommandExecuteResult  getExecuteResult() throws Exception {
		executeCommandString(cmdString);
		return result;
	}
	
	/**
	 * implement the Singleton pattern
	 * 
	 * @return the CommandExecutor instance
	 */
	// @author A0117215R
	public static CommandExecutor getInstance(String cmdString) {
		if(theOneCommandExecutor == null){
			theOneCommandExecutor = new CommandExecutor(cmdString);
		}
		return theOneCommandExecutor;
	}
}
