package com.tobedone.parser;
import com.tobedone.command.*;
import com.tobedone.parser.utils.CommandParser;
 
public class ClearCommandParser extends CommandParser{	
	public Command parseClearCommand(String paraString) {
		return new ClearCommand();
	}
}
