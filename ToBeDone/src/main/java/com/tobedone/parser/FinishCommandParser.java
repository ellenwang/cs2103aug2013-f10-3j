package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.FinishCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class FinishCommandParser extends CommandParser{
	int index;
	
	public FinishCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
			int indexofindex = paraString.indexOf(Constants.SPACE);
			index = parseIndex(indexofindex, paraString);
			
			return new FinishCommand(index);
	}
}