package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;

public class ListUnfinishedParser {
	public Command parse() {
		return new ListCommand("unfinished");
	}
}