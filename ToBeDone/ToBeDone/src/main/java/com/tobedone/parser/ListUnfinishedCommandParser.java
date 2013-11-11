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
 *        This class will generate an finish command with parameter "unfinished"
 */
public class ListUnfinishedCommandParser extends CommandParser {
	public Command parse(String paraString) {
		String unfinishedString = Constants.PARAMETER_LIST_UNFINISHED;
		return new ListCommand(unfinishedString);
	}
}