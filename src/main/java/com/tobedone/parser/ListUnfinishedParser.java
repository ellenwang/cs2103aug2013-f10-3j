package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a list command with parameter "all", 
 *        by parsing input command string
 */
public class ListUnfinishedParser {
	public Command parse() {
		return new ListCommand("unfinished");
	}
}