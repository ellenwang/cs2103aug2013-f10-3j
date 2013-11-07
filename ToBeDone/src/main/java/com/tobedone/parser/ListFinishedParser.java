package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;

public class ListFinishedParser {
	public Command parse() {
		return new ListCommand("finished");
	}
}