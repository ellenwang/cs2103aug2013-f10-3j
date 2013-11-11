package com.tobedone.parser;
import com.tobedone.command.*;
import com.tobedone.parser.utils.CommandParser;
 
public class HelpCommandParser extends CommandParser{	
	public Command parseHelpCommand(String paraString) {
		return new HelpCommand();
	}
}