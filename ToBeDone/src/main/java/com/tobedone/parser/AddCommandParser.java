package com.tobedone.parser;

import java.util.Date;

import com.tobedone.command.AddCommand;
import com.tobedone.command.Command;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

public class AddCommandParser extends CommandParser {
	Date startTime;
	Date endTime;
	Date deadline;
	String description;
	int priority;

	public AddCommandParser() {
		priority = Constants.INT_PRI_NORMAL;
	}

	public Command parse(String paraString) throws Exception {
		int indexoffrom = paraString
				.lastIndexOf(Constants.REGEX_DATE_FROM_PREFIX);
		int indexofto = paraString.lastIndexOf(Constants.REGEX_DATE_TO_PREFIX);
		int indexofby = paraString.lastIndexOf(Constants.REGEX_DATE_BY_PREFIX);
		int indexofpriority = Constants.NOT_FOUND_INDEX;

		if (paraString.endsWith(" " + Constants.STR_PRI_HIGH)
				|| paraString.endsWith(" " + Constants.STR_PRI_NORMAL)
				|| paraString.endsWith(" " + Constants.STR_PRI_LOW)
				|| paraString.endsWith(" " + Constants.STR_SHORT_PRI_HIGH)
				|| paraString.endsWith(" " + Constants.STR_SHORT_PRI_NORMAL)
				|| paraString.endsWith(" " + Constants.STR_SHORT_PRI_LOW)) {
			indexofpriority = paraString.lastIndexOf(Constants.SPACE)
					+ Constants.ONE_LOOKAHEAD;
		}

		if (indexoffrom != Constants.NOT_FOUND_INDEX
				&& indexofto != Constants.NOT_FOUND_INDEX
				&& indexofby == Constants.NOT_FOUND_INDEX) {
			description = paraString.substring(0, indexoffrom - 1);
			String endTimeString;

			String startTimeString = paraString.substring(indexoffrom + 5,
					indexofto - Constants.ONE_LOOKAHEAD);
			startTime = parseDate(startTimeString);

			if (indexofpriority != Constants.NOT_FOUND_INDEX) {
				endTimeString = paraString.substring(indexofto + 3,
						indexofpriority - 1);
			} else {
				endTimeString = paraString.substring(indexofto + 3);
			}
			endTime = parseDate(endTimeString);
		} else if (indexofby != Constants.NOT_FOUND_INDEX) {

			description = paraString.substring(0, indexofby - 1);
			String deadlineSting;

			if (indexofpriority != Constants.NOT_FOUND_INDEX) {
				deadlineSting = paraString.substring(indexofby + 3,
						indexofpriority - 1);
			} else {
				deadlineSting = paraString.substring(indexofby + 3);
			}
			deadline = parseDate(deadlineSting);
			System.out.println(deadline);
		} else {
			if (indexofpriority != Constants.NOT_FOUND_INDEX) {
				description = paraString.substring(0, indexofpriority - 1);
			} else {
				description = paraString;
			}
		}

		if (indexofpriority != Constants.NOT_FOUND_INDEX) {
			String priorityString = paraString.substring(indexofpriority);
			priority = parsePriority(priorityString);
		}

		if (description == null || priority == Constants.INT_PRI_WRONG) {
			throw new CommandWrongArgsException(
					Constants.MSG_ERROR_INVALID_ARGUMENT);
		}

		return new AddCommand(description, startTime, endTime, deadline,
				priority);
	}

}
