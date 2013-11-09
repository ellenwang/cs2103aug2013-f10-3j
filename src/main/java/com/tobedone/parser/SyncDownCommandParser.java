package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.GCalDownload;
import com.tobedone.parser.utils.CommandParser;

public class SyncDownCommandParser extends CommandParser {
	public Command parse(String paraString) {
		return new GCalDownload();
	}
}
