package com.tobedone.parser.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.Map;

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

	protected CommandParser() {
		currentYear = (Calendar.getInstance()).get(Calendar.YEAR)
				+ Constants.EMPTY_STRING;
		currentMonth = (Calendar.getInstance()).get(Calendar.MONTH)
				+ Constants.ONE + Constants.EMPTY_STRING;
		currentDay = (Calendar.getInstance()).get(Calendar.DAY_OF_MONTH)
				+ Constants.EMPTY_STRING;
		currentDayOfaWeek =  (Calendar.getInstance()).get(Calendar.DAY_OF_WEEK);
		
		commandmap = CommandParseMap.getInstance().getMap();
	}

	
	/**
	 * implement the Singleton pattern
	 * 
	 * @return the CommandParser instance
	 */
	// @author A0117215R
	public static CommandParser getInstance() {
		if (theOne == null) {
			theOne = new CommandParser();
		}
		return theOne;
	}
	
	
	// @author A0117215R
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

	
	/**
	 * get corresponding command parser according to the command type
	 * 
	 * @return the command name
	 */
	// @author A0117215R
	public CommandParser getCommandParser(String type) throws Exception {
		String commandClassName = Constants.PACK_PARSER + commandmap.get(type);
		CommandParser correspondingCmdParser = null;

		try {
			correspondingCmdParser = (CommandParser) Class.forName(commandClassName)
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new CommandNotFoundException(Constants.MSG_ERROR_INVALID);
		}
		return correspondingCmdParser;
	}
	
	
	// @author A0117215R
	protected int parseIndex(int indexofindex, String paraString) throws Exception{
		int index;
		if(indexofindex == Constants.NOT_FOUND_INDEX){
			throw new CommandWrongArgsException(Constants.MSG_ERROR_EMPTY_ARGUMENT_INDEX);
		}
		String indexString = paraString.substring(Constants.ZERO,indexofindex);
		try {
			index = Integer.parseInt(indexString);
		} catch (Exception e) {
			throw new CommandWrongArgsException(Constants.MSG_ERROR_INVALID_ARGUMENT_INDEX);
		}
		return index;
	}
	
	
	/**
	 * get date object from input
	 * 
	 * @return the date object
	 */
	//
	protected Date parseDate(String datePartern) throws Exception {
		String dayString = currentDay;
		String yearString = currentYear;
		String monthString = currentMonth;
		String time = Constants.DEFAULT_END_TIME;
		String timePartern = null;
		String dayPartern = null;
		Date date = null;
		String dateString = null;
		
		int indexOfday = datePartern.lastIndexOf(Constants.SPACE);
		int indexOftime = datePartern.indexOf(Constants.SPACE);

		if (indexOftime != Constants.NOT_FOUND_INDEX) {
			timePartern = datePartern.substring(0, indexOftime);
			dayPartern = datePartern.substring(indexOfday + Constants.ONE);
		}
		
		if (indexOftime != Constants.NOT_FOUND_INDEX
				&& timePartern.matches(Constants.REGEX_DATE_TIME)
				&& dayPartern.matches(Constants.REGEX_DATE_DAY)) {
			System.out.println("TDM");
			time = parseTime(timePartern);
			dayString = parseDay(dayPartern);
			dateString = time + Constants.COMMA + dayString + Constants.HYPHEN
					+ currentYear;
		}
		// HH:mm dd-mm-yyyy
		else if (indexOftime != Constants.NOT_FOUND_INDEX
				&& timePartern.matches(Constants.REGEX_DATE_TIME)
				&& dayPartern.matches(Constants.REGEX_DATE_DATE)) {
			System.out.println("TDMY");
			time = parseTime(timePartern);
			dayString = parseDay(dayPartern);
			dateString = time + Constants.COMMA + dayString;
		}
		// HH:mm
		else if (datePartern.matches(Constants.REGEX_DATE_TIME)) {
			System.out.println("T");
			dayString = currentDay + Constants.HYPHEN + currentMonth
					+ Constants.HYPHEN + currentYear;
			time = parseTime(datePartern);

			dateString = time + Constants.COMMA + dayString;
		}
		// dd-mm
		else if (datePartern.matches(Constants.REGEX_DATE_DAY)) {
			System.out.println("DM");
			dayString = parseDay(datePartern);
			dateString = time + Constants.COMMA + dayString + Constants.HYPHEN
					+ currentYear;
		}
		// dd-mm-yyyy
		else if (datePartern.matches(Constants.REGEX_DATE_DATE)) {
			System.out.println("DMY");
			dayString = parseDay(datePartern);
			dateString = time + Constants.COMMA + dayString;
		}
		// short of day
		else if (datePartern.matches(Constants.REGEX_DATE_SHORT)) {
			int futureday = getFutureDay(datePartern);
			System.out.println(futureday);
				dateString = time + Constants.COMMA + futureday
						+ Constants.HYPHEN + monthString + Constants.HYPHEN
						+ yearString;	
		}
		// wrong date format
		else {
			throw new WrongDateFormatException(Constants.MSG_ERROR_WRONG_DATE);
		}
		System.out.println("format date"+dateString);
		try {
			date = Constants.simpleDateFormat.parse(dateString);
		} catch (Exception e) {
			throw new WrongDateFormatException(Constants.MSG_ERROR_WRONG_DATE);
		}

		return date;
	}

	private String parseTime(String timePartern) throws Exception {
		int indexOfAM = timePartern.toLowerCase().indexOf(Constants.TIME_AM);
		int indexOfPM = timePartern.toLowerCase().indexOf(Constants.TIME_PM);
		int indexOfColon = timePartern.indexOf(Constants.COLON);
		String time = null;
		int hourof24format;

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
				//eg. change 1pm to 13:00
				hourof24format = Integer.parseInt(oldTime) + Constants.TWEVEL;
				//eg. 13pm is still 13:00
				if (hourof24format > 24) {
					hourof24format = hourof24format - Constants.TWEVEL;	
				}
				time = hourof24format + Constants.EMPTY_STRING
						+ Constants.COLON + Constants.CLOCK;
			} else {
				//eg. change 1:30pm to 13:30
				hourof24format = Integer.parseInt(timePartern.substring(
						Constants.ZERO, indexOfColon)) + Constants.TWEVEL;
				//eg. 13:30pm is still 13:30
				if (hourof24format > 24) {
					hourof24format = hourof24format - Constants.TWEVEL;
				} 
				time = hourof24format + Constants.EMPTY_STRING
						+ Constants.COLON
						+ oldTime.substring(indexOfColon + 1);
			}
		} else {
			time = timePartern;
		}
		return time;
	}

	private String parseDay(String datePartern) {
		datePartern=datePartern.replace('.', '-');
		datePartern=datePartern.replace('\\', '-');
		datePartern=datePartern.replace('/', '-');
		datePartern=datePartern.replace(':', '-');

		System.out.println(datePartern);
		return datePartern;
	}
	
	private int getFutureDay(String dayofweek){
		int currentday = Integer.parseInt(currentDay);
		switch (dayofweek) {
		case "today":
			return currentday+0;
		case "tomorrow":
			return currentday+1;
		case "sun" :
			if(currentDayOfaWeek>Calendar.SUNDAY){
				return currentday+Calendar.SUNDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.SUNDAY-currentDayOfaWeek;
			}
		case "mon" :
			if(currentDayOfaWeek>Calendar.MONDAY){
				return currentday+Calendar.MONDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.MONDAY-currentDayOfaWeek;
			}
		case "tues" :
			if(currentDayOfaWeek>Calendar.TUESDAY){
				return currentday+Calendar.TUESDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.TUESDAY-currentDayOfaWeek;
			}
		case "wed" :
			if(currentDayOfaWeek>Calendar.WEDNESDAY){
				return currentday+Calendar.WEDNESDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.WEDNESDAY-currentDayOfaWeek;
			}
		case "thur" :
			if(currentDayOfaWeek>Calendar.THURSDAY){
				return currentday+Calendar.THURSDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.THURSDAY-currentDayOfaWeek;
			}
		case "fri" :
			if(currentDayOfaWeek>Calendar.FRIDAY){
				return currentday+Calendar.FRIDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.FRIDAY-currentDayOfaWeek;
			}
		case "sat" :
			if(currentDayOfaWeek>Calendar.SATURDAY){
				return currentday+Calendar.SATURDAY-currentDayOfaWeek + 7;
			}else{
				return currentday+Calendar.SATURDAY-currentDayOfaWeek;
			}		
		}
		return currentday;
	}
}
