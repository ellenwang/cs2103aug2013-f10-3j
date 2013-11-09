package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.RemoveCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a remove command by parsing input command string
 *        It will throws exception when the index is wrong
 */
public class RemoveCommandParser extends CommandParser{
	int index;
	
	public RemoveCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
			index = parseIndex(paraString.length(), paraString);
			return new RemoveCommand(index);
		} 
	}
