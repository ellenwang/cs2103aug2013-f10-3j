package com.tobedone.parser;
import com.tobedone.command.*;
import com.tobedone.parser.utilities.CommandParser;
 
public class UndoCommandParser extends CommandParser{	
	public Command parseUndoCommand(String paraString) {
		return new UndoCommand();
	}
}