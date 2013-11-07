package com.tobedone.parser;
import com.tobedone.command.*;
import com.tobedone.parser.utils.CommandParser;
 
public class SyncCommandParser extends CommandParser{	
	public Command parse(String paraString) {
		return new GCalCommand();
	}
}
