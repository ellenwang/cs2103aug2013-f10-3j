package com.tobedone.parser;

import java.util.Date;
import java.util.regex.Matcher;

import com.tobedone.command.Command;
import com.tobedone.command.UpdateCommand;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class will generate an update command by parsing input command string It
 *        will throws exception when the index is wrong
 */
public class UpdateCommandParser extends CommandParser {
	int index;
	Date newstartTime;
	Date newendTime;
	Date newdeadline;
	String newdescription;
	int newpriority;

	public UpdateCommandParser() {
		newpriority = Constants.INT_PRI_NO_CHANGE;
	}

	//@author A0117215R
	public Command parse(String paraString) throws Exception {
		String startTimeString = null;
		String endTimeString = null;
		String deadlineString = null;
		int endOfDescription = Constants.NOT_FOUND_INDEX;
		int indexofindex = Constants.NOT_FOUND_INDEX;
		int indexofpriority = Constants.NOT_FOUND_INDEX;

		Matcher matcher;
		
		int beginfromto = Constants.NOT_FOUND_INDEX;
		int endfromto = Constants.NOT_FOUND_INDEX;
		String fromtoString = null;
		
		int beginfrom = Constants.NOT_FOUND_INDEX;
		String fromString = null;
		
		int beginto = Constants.NOT_FOUND_INDEX;
		String toString = null;
		
		int beginby = Constants.NOT_FOUND_INDEX;
		String byString = null;

		// extract index from paraString and then remove it from paraString
		indexofindex = paraString.indexOf(Constants.SPACE);
		index = parseIndex(indexofindex, paraString);
		paraString = paraString.substring(indexofindex + 1);

		// extract priority from paraString and then remove it from paraString
		if (paraString.endsWith(Constants.SPACE + Constants.STR_PRI_HIGH)
				|| paraString.endsWith(Constants.SPACE
						+ Constants.STR_PRI_NORMAL)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_PRI_LOW)
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
			newpriority = parsePriority(priorityString);
			paraString = paraString.substring(0, indexofpriority);
		}

		// find the last "from to" string
		matcher = Constants.FROM_TO_PATTERN.matcher(paraString);
		while (matcher.find()) {
			beginfromto = matcher.start();
			endfromto = matcher.end();
			fromtoString = matcher.group(0);
		}

		// find the last "from Date" string
		matcher = Constants.FROM_PATTERN.matcher(paraString);
		while (matcher.find()) {
			beginfrom = matcher.start();
			fromString = matcher.group(0);
		}

		// find the last "from Date" string
		matcher = Constants.TO_PATTERN.matcher(paraString);
		while (matcher.find()) {
			beginto = matcher.start();
			toString = matcher.group(0);
		}

		// find the last "from Date" string
		matcher = Constants.BY_PATTERN.matcher(paraString);
		while (matcher.find()) {
			beginby = matcher.start();
			byString = matcher.group(0);
		}

		int maxindex = Math.max(Math.max(beginby, beginfromto),
				Math.max(beginfrom, beginto));
		
		//case1: XXXXXXX by newDate
		if (beginby == maxindex 
				&& byString!=null
				&& paraString.endsWith(byString)) {
			deadlineString = byString;
			newdeadline = parseDate(deadlineString, false);

			endOfDescription = beginby;
		} 
		//case2: XXXXXX from newDate1 to newDate2
		else if (endfromto > maxindex 
				&& fromtoString != null
				&& paraString.endsWith(fromtoString)) {
			matcher = Constants.FROM_PATTERN.matcher(fromtoString);
			if (matcher.find()) {
				startTimeString = matcher.group(0);
			}
			newstartTime = parseDate(startTimeString, true);

			endOfDescription = matcher.start();

			matcher = Constants.TO_PATTERN.matcher(fromtoString);
			if (matcher.find()) {
				endTimeString = matcher.group(0);
			}
			newendTime = parseDate(endTimeString, false);

			endOfDescription = beginfromto;
		} 
		//case3: XXXXX from newDate
		else if (beginfrom == maxindex 
				&& fromString != null
				&& paraString.endsWith(fromString)) {
			startTimeString = fromString;
			newstartTime = parseDate(startTimeString, true);

			endOfDescription = beginfrom;
		} 
		//case4: XXXXXX to newDate
		else if (beginto == maxindex 
				&& toString!= null
				&& paraString.endsWith(toString)
				&& beginto > endfromto) {
			endTimeString = toString;
			newendTime = parseDate(endTimeString, false);

			endOfDescription = beginto;
		} 
		//case newDescription
		else {
			endOfDescription = paraString.length();
		}
		
		if (endOfDescription > 0) {
			newdescription = paraString.substring(0, endOfDescription);
		}

		return new UpdateCommand(index, newdescription, newstartTime,
				newendTime, newdeadline, newpriority);
	}
}
