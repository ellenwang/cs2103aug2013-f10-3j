package com.tobedone.parser;
import com.tobedone.command.*;
import com.tobedone.parser.utils.CommandParser;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a clear command by parsing input command string It
 *        
 */
public class ClearCommandParser extends CommandParser{	
	public Command parse(String paraString) {
		return new ClearCommand();
	}
}
