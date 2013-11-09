package com.tobedone.parser;

import com.tobedone.command.Command;
import com.tobedone.command.ListCommand;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate a list command with parameter "all", 
 *        by parsing input command string
 */
public class ListAllParser {
	public Command parse() {
		return new ListCommand(Constants.PARAMETER_LIST_ALL);
	}
}
