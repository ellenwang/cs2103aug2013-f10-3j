package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.RemoveCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class RemoveCommandParser extends CommandParser{
	int index;
	
	public RemoveCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
			index = parseIndex(paraString.length(), paraString);
			System.out.println("Index: " + index);
			return new RemoveCommand(index);
		} 
	}
