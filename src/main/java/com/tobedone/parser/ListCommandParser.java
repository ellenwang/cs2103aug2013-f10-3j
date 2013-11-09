package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a list command by parsing input command string 
 *        It will throws exception when there is no argument found
 */
public class ListCommandParser extends CommandParser {
	public Command parse(String paraString) throws Exception {
		if(paraString==null){
			throw new CommandWrongArgsException(Constants.MSG_ERROR_EMPTY_ARGUMENT);
		}
		return new ListCommand(paraString);
	}
}