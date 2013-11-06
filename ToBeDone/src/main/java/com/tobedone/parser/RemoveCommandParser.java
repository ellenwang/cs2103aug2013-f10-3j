package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.RemoveCommand;
import com.tobedone.parser.utilities.CommandParser;
import com.tobedone.utilities.Constants;

public class RemoveCommandParser extends CommandParser{
	int index;
	
	public RemoveCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
			int indexofindex = paraString.indexOf(Constants.SPACE);
			index = parseIndex(indexofindex, paraString);
			
			return new RemoveCommand(index);
		} 
	}
