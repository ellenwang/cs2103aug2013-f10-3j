package com.tobedone.ui;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.logic.CommandExecutor;
import com.tobedone.utils.Constants;

/**
 * @author Tian Xing
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
	
	protected static void executeCommands() throws Exception {
		if((!commandString.equals(Constants.EXIT_TOBEDONE))) {
			try {
				executor = CommandExecutor.getInstance(commandString);
				commandExecuteResult = executor.getExecuteResult();
//				System.out.println(commandExecuteResult.getAimTasks());
			} catch (Exception e) {
				throw e;
			}
		} else {
			System.exit(0);
		}
	}

	protected static void readUserInput(String input) {
		commandString = input;
	}
	
	protected static CommandExecuteResult getCommandExecuteResult() {
		return commandExecuteResult;
	}

}
