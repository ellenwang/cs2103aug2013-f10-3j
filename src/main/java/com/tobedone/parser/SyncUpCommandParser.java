package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.GCalUpload;
import com.tobedone.parser.utils.CommandParser;

public class SyncUpCommandParser extends CommandParser {
	public Command parse(String paraString) {
		return new GCalUpload();
	}
}
