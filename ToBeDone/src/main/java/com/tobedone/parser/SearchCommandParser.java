package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.SearchCommand;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.exception.WrongDateFormatException;
import com.tobedone.parser.utils.CommandParser;

public class SearchCommandParser extends CommandParser {
	public Command parse(String paraString) throws Exception {
		if(paraString==null){
			throw new CommandWrongArgsException();
		}
		return new SearchCommand(paraString);
	}
}
