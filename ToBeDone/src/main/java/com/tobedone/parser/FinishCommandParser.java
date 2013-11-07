package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.FinishCommand;
import com.tobedone.command.RemoveCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class FinishCommandParser extends CommandParser{
	int index;
	
	public FinishCommandParser() {
		index = Constants.NOT_FOUND_INDEX;
	}
	
	public Command parse(String paraString) throws Exception{
		index = parseIndex(paraString.length(), paraString);
		return new RemoveCommand(index);
	}
}