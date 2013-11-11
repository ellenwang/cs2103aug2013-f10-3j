package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.SearchCommand;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class SearchCommandParser extends CommandParser {
	public Command parse(String paraString) throws Exception {
		if(paraString==null){
			throw new CommandWrongArgsException(Constants.MSG_ERROR_EMPTY_ARGUMENT);
		}
		assert(paraString!=null);
		return new SearchCommand(paraString);
	}
}
