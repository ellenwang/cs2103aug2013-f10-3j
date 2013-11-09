package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.FinishCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a finish command by parsing input command string It
 *        will throws exception when the index is wrong
 */
public class FinishCommandParser extends CommandParser{
	int index;
	
	public FinishCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	//@author A0117215R
	public Command parse(String paraString) throws Exception{
		index = parseIndex(paraString.length(), paraString);
		return new FinishCommand(index);
	}
}