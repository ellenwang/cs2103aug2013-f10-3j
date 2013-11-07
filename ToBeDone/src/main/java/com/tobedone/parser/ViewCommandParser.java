package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ViewCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class ViewCommandParser extends CommandParser{
	int index;
	
	public ViewCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
			int indexofindex = paraString.indexOf(Constants.SPACE);
			index = parseIndex(indexofindex, paraString);	
			return new ViewCommand(index);
		} 
	}
