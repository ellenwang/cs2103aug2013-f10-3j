package com.tobedone.parser;

import java.util.Date;
import java.util.regex.Matcher;

import com.tobedone.command.AddCommand;
import com.tobedone.command.Command;
import com.tobedone.exception.CommandWrongArgsException;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate an add command by parsing input command string It
 *        will throws exception when the description is empty
 */
public class AddCommandParser extends CommandParser {
	Date startTime;
	Date endTime;
	Date deadline;
	String description;
	int priority;

	public AddCommandParser() {
		priority = Constants.INT_PRI_NORMAL;
	}
	
	// @author A0117215R
	public Command parse(String paraString) throws Exception {
		try {
			String startTimeString = null;
			String endTimeString = null;
			String deadlineString = null;
			int endOfDescription;

			Matcher matcher;
			int indexofpriority = Constants.NOT_FOUND_INDEX;
			int indexfromto = Constants.NOT_FOUND_INDEX;
			String fromtoString = null;
			int indexby = Constants.NOT_FOUND_INDEX;
			String byString = null;

			// extract priority from the paraString (if has)
			if (paraString.endsWith(Constants.SPACE + Constants.STR_PRI_HIGH)
					|| paraString.endsWith(Constants.SPACE
							+ Constants.STR_PRI_NORMAL)
					|| paraString.endsWith(Constants.SPACE
							+ Constants.STR_PRI_LOW)
					|| paraString.endsWith(Constants.SPACE
							+ Constants.STR_SHORT_PRI_HIGH)
					|| paraString.endsWith(Constants.SPACE
							+ Constants.STR_SHORT_PRI_NORMAL)
					|| paraString.endsWith(Constants.SPACE
							+ Constants.STR_SHORT_PRI_LOW)) {
				indexofpriority = paraString.lastIndexOf(Constants.SPACE)
						+ Constants.ONE_LOOKAHEAD;
			}
			if (indexofpriority != Constants.NOT_FOUND_INDEX) {
				String priorityString = paraString.substring(indexofpriority);
				priority = parsePriority(priorityString);
				paraString = paraString.substring(0, indexofpriority);
			}

			// find the last "from Date1 to Date2" string
			matcher = Constants.FROM_TO_PATTERN.matcher(paraString);
			while (matcher.find()) {
				fromtoString = matcher.group(0);
				indexfromto = matcher.start();
			}

			// find the last "by Date" string
			matcher = Constants.BY_PATTERN.matcher(paraString);
			while (matcher.find()) {
				byString = matcher.group(0);
				indexby = matcher.start();
			}

			// case1: XXXXX from Date1 to Date2
			if (indexfromto > indexby && paraString.endsWith(fromtoString)) {
				matcher = Constants.FROM_PATTERN.matcher(paraString);
				while (matcher.find()) {
					startTimeString = matcher.group(0);
				}
				startTime = parseDate(startTimeString, true);

				endOfDescription = indexfromto;

				matcher = Constants.TO_PATTERN.matcher(paraString);
				while (matcher.find()) {
					endTimeString = matcher.group(0);
				}
				endTime = parseDate(endTimeString, false);
			}
			// case2: XXXXXX by Date
			else if (indexby > indexfromto && paraString.endsWith(byString)) {
				matcher = Constants.BY_PATTERN.matcher(paraString);
				while (matcher.find()) {
					deadlineString = matcher.group(0);
				}
				deadline = parseDate(deadlineString, false);

				endOfDescription = indexby;
			}
			// case3: XXXXXX
			else {
				endOfDescription = paraString.length();
			}

			// Wrong: the description is null!
			if (endOfDescription > 0) {
				description = paraString.substring(0, endOfDescription);
			} else {
				throw new CommandWrongArgsException(
						Constants.MSG_ERROR_EMPTY_DESCRIPTION);
			}

			return new AddCommand(description, startTime, endTime, deadline,
					priority);
		} catch (Exception e) {
			throw e;
		}
	}

	//below methods are used for testing
	protected Date getStartTime() {
		return startTime;
	}


	protected Date getEndTime() {
		return endTime;
	}


	protected Date getDeadline() {
		return deadline;
	}

	protected String getDescription() {
		return description;
	}


	protected int getPriority() {
		return priority;
	}
	
	protected void restart() {
		description = null;
		startTime = null;
		endTime = null;
		deadline = null;
		priority = Constants.INT_PRI_NORMAL;
	}
	
	public String toString() {
		return Constants.CMD_ADD;
	}
	
}
