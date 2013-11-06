package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;

public class ListAllParser {
	public Command parse() {
		return new ListCommand("all");
	}
}
