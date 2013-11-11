package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class ListCommandParser extends CommandParser {
	public Command parse(String paraString) throws Exception {
		
		String all = Constants.PARAMETER_LIST_ALL;
		String finished = Constants.PARAMETER_LIST_FINISHED;
		String unfinished = Constants.PARAMETER_LIST_UNFINISHED; 
		
		if(paraString==null
				||(!paraString.equals(all)
				&&!paraString.equals(finished)
				&&!paraString.equals(unfinished))){
			throw new CommandWrongArgsException();
		}
		return new ListCommand(paraString);
	}
}