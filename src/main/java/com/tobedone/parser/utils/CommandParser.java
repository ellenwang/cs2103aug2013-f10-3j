package com.tobedone.parser.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;

import com.tobedone.utils.Constants;
import com.tobedone.command.Command;
import com.tobedone.exception.*;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class applies factory pattern for initialization of new commands.
 * 
 */
public class CommandParser {
	private Map<String, String> commandmap;
	private static CommandParser theOne;
	private String currentYear;
	private String currentDay;
	private String currentMonth;
	private int currentDayOfaWeek;

	// author A0117215R
	// get current date info
	protected CommandParser() {
		currentYear = (Calendar.getInstance()).get(Calendar.YEAR)
				+ Constants.EMPTY_STRING;
		currentMonth = (Calendar.getInstance()).get(Calendar.MONTH)
				+ Constants.ONE + Constants.EMPTY_STRING;
		currentDay = (Calendar.getInstance()).get(Calendar.DAY_OF_MONTH)
				+ Constants.EMPTY_STRING;
		currentDayOfaWeek = (Calendar.getInstance()).get(Calendar.DAY_OF_WEEK);

		commandmap = CommandParseMap.getInstance().getMap();
	}

	// @author A0117215R
	/**
	 * implement the Singleton pattern
	 * 
	 * @return the CommandParser instance
	 */
	public static CommandParser getInstance() {
		if (theOne == null) {
			theOne = new CommandParser();
		}
		return theOne;
	}

	// @author A0117215R
	// this method will be overiding by it subclasses
	public Command parse(String parasString) throws Exception {
		return null;
	}

	// @author A0117215R
	protected int parsePriority(String priorityString) {
		switch (priorityString) {
		case Constants.STR_PRI_HIGH:
			return Constants.INT_PRI_HIGH;
		case Constants.STR_PRI_NORMAL:
			return Constants.INT_PRI_NORMAL;
		case Constants.STR_PRI_LOW:
			return Constants.INT_PRI_LOW;
		case Constants.STR_SHORT_PRI_HIGH:
			return Constants.INT_PRI_HIGH;
		case Constants.STR_SHORT_PRI_NORMAL:
			return Constants.INT_PRI_NORMAL;
		case Constants.STR_SHORT_PRI_LOW:
			return Constants.INT_PRI_LOW;
		}
		return Constants.INT_PRI_WRONG;
	}

	// @author A0117215R
	/**
	 * get corresponding command parser according to the command type
	 * 
	 * @return the command name
	 */
	public CommandParser getCommandParser(String type) throws Exception {
		String commandClassName = Constants.PACK_PARSER + commandmap.get(type);
		CommandParser correspondingCmdParser = null;

		try {
			correspondingCmdParser = (CommandParser) Class.forName(
					commandClassName).newInstance();
		} catch (ClassNotFoundException e) {
			throw new CommandNotFoundException(Constants.MSG_ERROR_INVALID);
		}
		return correspondingCmdParser;
	}

	// @author A0117215R
	protected int parseIndex(int indexofindex, String paraString)
			throws Exception {
		int index;
		if (indexofindex == Constants.NOT_FOUND_INDEX) {
			throw new CommandWrongArgsException(
					Constants.MSG_ERROR_EMPTY_ARGUMENT_INDEX);
		}
		String indexString = paraString.substring(Constants.ZERO, indexofindex);
		try {
			index = Integer.parseInt(indexString);
		} catch (Exception e) {
			throw new CommandWrongArgsException(
					Constants.MSG_ERROR_INVALID_ARGUMENT_INDEX);
		}
		return index;
	}

	/**
	 * get date object from input date pattern
	 * 
	 * @return the date object
	 */
	// @author A0117215R
	protected Date parseDate(String datePattern, Boolean isFromTime)
			throws Exception {
		String dayString = currentDay;
		String yearString = currentYear;
		String monthString = currentMonth;
		String time;

		if (isFromTime) {
			time = Constants.DEFAULT_START_TIME;
		} else {
			time = Constants.DEFAULT_END_TIME;
		}

		int indexOftime = Constants.NOT_FOUND_INDEX;
		int indexOfday = Constants.NOT_FOUND_INDEX;
		String timePartern = null;
		String dayPartern = null;
		Date date = null;
		String dateString = null;

		// extract "from/to/by" prefix from the datePattern
		Matcher matcher;
		matcher = Constants.ONE_DATE_PATTERN.matcher(datePattern);
		if (matcher.find()) {
			datePattern = matcher.group(0);
		}

		// find HH:mm from ONE_DATE
		matcher = Constants.TIME_PATTERN.matcher(datePattern);
		if (matcher.find()) {
			indexOftime = matcher.end();
		}

		// extract HH:mm from the date pattern
		if (indexOftime != Constants.NOT_FOUND_INDEX) {
			if (indexOftime == datePattern.length()) {
				timePartern = datePattern;
			} else {
				timePartern = datePattern.substring(0, indexOftime);
			}
		}

		// find dd-mm from the date pattern
		matcher = Constants.DAY_PATTERN.matcher(datePattern);
		if (matcher.find()) {
			indexOfday = matcher.start();
			dayPartern = datePattern.substring(indexOfday);
		}

		// match HH:mm dd-mm
		if (indexOftime != Constants.NOT_FOUND_INDEX
				&& indexOfday != Constants.NOT_FOUND_INDEX
				&& timePartern.matches(Constants.REGEX_DATE_TIME)
				&& dayPartern.matches(Constants.REGEX_DATE_DAY)) {
			time = parseTime(timePartern);
			dayString = parseDay(dayPartern);
			dateString = time + Constants.COMMA + dayString + Constants.HYPHEN
					+ currentYear;
		}
		// match HH:mm dd-mm-yyyy
		else if (indexOftime != Constants.NOT_FOUND_INDEX
				&& indexOfday != Constants.NOT_FOUND_INDEX
				&& timePartern.matches(Constants.REGEX_DATE_TIME)
				&& dayPartern.matches(Constants.REGEX_DATE_DATE)) {
			time = parseTime(timePartern);
			dayString = parseDay(dayPartern);
			dateString = time + Constants.COMMA + dayString;
		}
		// match HH:mm
		else if (datePattern.matches(Constants.REGEX_DATE_TIME)) {
			dayString = currentDay + Constants.HYPHEN + currentMonth
					+ Constants.HYPHEN + currentYear;
			time = parseTime(datePattern);

			dateString = time + Constants.COMMA + dayString;
		}
		// match dd-mm
		else if (datePattern.matches(Constants.REGEX_DATE_DAY)) {
			dayString = parseDay(datePattern);
			dateString = time + Constants.COMMA + dayString + Constants.HYPHEN
					+ currentYear;
		}
		// match dd-mm-yyyy
		else if (datePattern.matches(Constants.REGEX_DATE_DATE)) {
			dayString = parseDay(datePattern);
			dateString = time + Constants.COMMA + dayString;
		}
		// match short of day
		else if (datePattern.matches(Constants.REGEX_SHORT_DAY)) {
			int futureday = getFutureDay(datePattern);
			dateString = time + Constants.COMMA + futureday + Constants.HYPHEN
					+ monthString + Constants.HYPHEN + yearString;
		}
		// wrong date format
		else {
			throw new WrongDateFormatException(Constants.MSG_ERROR_WRONG_DATE);
		}
		try {
			date = Constants.simpleDateFormat.parse(dateString);
		} catch (Exception e) {
			throw new WrongDateFormatException(Constants.MSG_ERROR_WRONG_DATE);
		}

		return date;
	}

	// @author A0117215R
	private String parseTime(String timePartern) throws Exception {
		int indexOfAM = timePartern.toLowerCase().indexOf(Constants.TIME_AM);
		int indexOfPM = timePartern.toLowerCase().indexOf(Constants.TIME_PM);
		int indexOfColon = timePartern.indexOf(Constants.COLON);
		String time = null;
		int hourof24format;

		//
		if (indexOfAM != Constants.NOT_FOUND_INDEX) {
			String oldTime = timePartern.substring(0, indexOfAM);

			if (indexOfColon == Constants.NOT_FOUND_INDEX) {
				time = oldTime + Constants.COLON + Constants.CLOCK;
			} else {
				time = oldTime;
			}
		} else if (indexOfPM != Constants.NOT_FOUND_INDEX) {
			String oldTime = timePartern.substring(0, indexOfPM);

			if (indexOfColon == Constants.NOT_FOUND_INDEX) {
				// eg. change 1pm is 13:00
				hourof24format = Integer.parseInt(oldTime)
						+ Constants.HOURS_OF_A_DAY;
				// eg. 13pm is still 13:00
				if (hourof24format > 24) {
					hourof24format = hourof24format - Constants.HOURS_OF_A_DAY;
				}
				time = hourof24format + Constants.EMPTY_STRING
						+ Constants.COLON + Constants.CLOCK;
			} else {
				// eg. change 1:30pm is 13:30
				hourof24format = Integer.parseInt(timePartern.substring(
						Constants.ZERO, indexOfColon))
						+ Constants.HOURS_OF_A_DAY;
				// eg. 13:30pm is still 13:30
				if (hourof24format > 24) {
					hourof24format = hourof24format - Constants.HOURS_OF_A_DAY;
				}
				time = hourof24format + Constants.EMPTY_STRING
						+ Constants.COLON + oldTime.substring(indexOfColon + 1);
			}
		} else {
			time = timePartern;
		}
		return time;
	}

	// @author A0117215R
	protected int getPriorityIndex(String paraString) {
		int indexofpriority = Constants.NOT_FOUND_INDEX;

		if (paraString.endsWith(Constants.STR_PRI_HIGH)
				|| paraString.endsWith(Constants.STR_PRI_NORMAL)
				|| paraString.endsWith(Constants.STR_PRI_LOW)
				|| paraString.endsWith(Constants.STR_PRI_LOW)) {
			indexofpriority = paraString.lastIndexOf(Constants.SPACE)
					+ Constants.ONE_LOOKAHEAD;
		}

		return indexofpriority;
	}

	// @author A0117215R
	private String parseDay(String datePartern) {
		datePartern = datePartern.replace('.', '-');
		datePartern = datePartern.replace('\\', '-');
		datePartern = datePartern.replace('/', '-');
		datePartern = datePartern.replace(':', '-');

		return datePartern;
	}

	
	// @author A0117215R
	/**
	 * if today is Wed, when user input "this mon", it will be mon this week
	 * if user just input mon or "next mon", it will be mon next week
	 * however
	 * if user input "this fri" or just "fri", it will be fri week
	 * only "next fri" will be next fri.
	 * 
	 * @return the command name
	 */
	private int getFutureDay(String dayofweek) {
		int currentday = Integer.parseInt(currentDay);
		int addDays1 = 0;
		int addDays2 = 0;

		if (dayofweek.contains("next")) {
			addDays1 = 0;
			addDays2 = Constants.DAYS_OF_A_WEEK;
		}

		if (dayofweek.contains("this")) {
			addDays1 = 0-Constants.DAYS_OF_A_WEEK;
		}

		Matcher matcher = Constants.SHORT_DAY_PATTERN.matcher(dayofweek);
		if (matcher.find()) {
			dayofweek = matcher.group(0);
		}

		if (dayofweek.equals("today")) {
			return currentday;
		} else if (dayofweek.equals("tomorrow")) {
			return currentday + 1;
		} else if (dayofweek.equals("mon") || dayofweek.equals("monday")) {
			if (currentDayOfaWeek > Calendar.MONDAY) {
				return currentday + Calendar.MONDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.MONDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else if (dayofweek.equals("tues") || dayofweek.equals("tuesday")) {
			if (currentDayOfaWeek > Calendar.TUESDAY) {
				return currentday + Calendar.TUESDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.TUESDAY - currentDayOfaWeek
						+ addDays2;
			}

		} else if (dayofweek.equals("wed") || dayofweek.equals("wednesday")) {
			if (currentDayOfaWeek > Calendar.WEDNESDAY) {
				return currentday + Calendar.WEDNESDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.WEDNESDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else if (dayofweek.equals("thur") || dayofweek.equals("thursday")) {
			if (currentDayOfaWeek > Calendar.THURSDAY) {
				return currentday + Calendar.THURSDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.THURSDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else if (dayofweek.equals("fri") || dayofweek.equals("friday")) {
			if (currentDayOfaWeek > Calendar.FRIDAY) {
				return currentday + Calendar.FRIDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.FRIDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else if (dayofweek.equals("sat") || dayofweek.equals("saturday")) {
			if (currentDayOfaWeek > Calendar.SATURDAY) {
				return currentday + Calendar.SATURDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.SATURDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else if (dayofweek.equals("sun") || dayofweek.equals("sunday")) {
			if (currentDayOfaWeek > Calendar.SUNDAY) {
				return currentday + Calendar.SUNDAY - currentDayOfaWeek
						+ addDays1 + Constants.DAYS_OF_A_WEEK;
			} else {
				return currentday + Calendar.SUNDAY - currentDayOfaWeek
						+ addDays2;
			}
		} else {
			return currentday;
		}
	}
}
