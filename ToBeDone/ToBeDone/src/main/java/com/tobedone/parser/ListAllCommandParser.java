package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate an finish command with parameter "all"
 */
public class ListAllCommandParser extends CommandParser {
	public Command parse(String paraString) {
		String allString = Constants.PARAMETER_LIST_ALL;
		return new ListCommand(allString);
	}
}
