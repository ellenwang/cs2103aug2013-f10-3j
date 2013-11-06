package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.exception.WrongDateFormatException;
import com.tobedone.parser.utilities.CommandParser;

public class ListCommandParser extends CommandParser {
	public Command parse(String paraString) throws Exception {
		if(paraString==null){
			throw new CommandWrongArgsException();
		}
		return new ListCommand(paraString);
	}
}