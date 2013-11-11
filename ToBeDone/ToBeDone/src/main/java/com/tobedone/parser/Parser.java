package com.tobedone.parser;

import com.tobedone.command.*;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate an command by calling corresponding commandParser 
 *        according to the command type. 
 */
public class Parser {	
	private static Parser theOne;
	private static CommandParser commandParser;
	
	private Parser(){
		commandParser = CommandParser.getInstance();
	}

	
	// @author A0117215R
	/**
	 * generate command object from user input
	 * 
	 * @return the command
	 */
	public Command parseCommand(String commandString) throws Exception {
		String comType = extractCommandType(commandString);
		comType = comType.toLowerCase();
		String comParas = extractCommandParas(commandString) ;
		Command command = null;
		CommandParser correspondCmdParser;
		
		correspondCmdParser = commandParser.getCommandParser(comType);
		command = correspondCmdParser.parse(comParas);
		return command;
	}
	
	/**
	 * Extract the command type from user input
	 * 
	 * @return the command type
	 */
	// @author A0117215R
	protected String extractCommandType(String input) {
		int indexOfSpace = input.indexOf(Constants.SPACE);
		String commandType = input;
		if (indexOfSpace != Constants.SERVICE_OPERATION_FAILED_ID) {
			commandType = input.substring(0, indexOfSpace);
		}
		return commandType;
	}
	
	/**
	 * Extract the command parameters from user input
	 * 
	 * @return the command parameters string
	 */
	// @author A0117215R
	protected String extractCommandParas(String input) {
		int indexOfSpace = input.indexOf(Constants.SPACE);
		String commandParas = null;
		if (indexOfSpace != Constants.SERVICE_OPERATION_FAILED_ID) {
			commandParas = input.substring(indexOfSpace+Constants.ONE);
		}
		return commandParas;
	}
	
	/**
	 * implement the Singleton pattern
	 * 
	 * @return the Parser instance
	 */
	// @author A0117215R
	public static Parser getInstance() {
		if(theOne == null){
			theOne = new Parser();
		}
		return theOne;
		
	}
}
