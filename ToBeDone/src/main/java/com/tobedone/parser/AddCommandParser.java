package com.tobedone.parser;

import java.util.Date;
import java.util.regex.Matcher;

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
		try{
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
		
		//extract priority from the paraString (if has)
		if (paraString.endsWith(Constants.SPACE + Constants.STR_PRI_HIGH)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_PRI_NORMAL)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_PRI_LOW)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_SHORT_PRI_HIGH)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_SHORT_PRI_NORMAL)
				|| paraString.endsWith(Constants.SPACE + Constants.STR_SHORT_PRI_LOW)) {
			indexofpriority = paraString.lastIndexOf(Constants.SPACE)
					+ Constants.ONE_LOOKAHEAD;
		}
		if (indexofpriority!=Constants.NOT_FOUND_INDEX) {
			String priorityString = paraString.substring(indexofpriority);
			priority = parsePriority(priorityString);
			paraString = paraString.substring(0,indexofpriority);
			System.out.println("paraString remove priority: "+paraString);
		}
		
		//find the last "from Date1 to Date2" string 
		matcher =  Constants.FROM_TO_PATTERN.matcher(paraString);
		while(matcher.find()){
			fromtoString = matcher.group(0);
			indexfromto = matcher.start();
		}
		
		//find the last "by Date" string
		matcher = Constants.BY_PATTERN.matcher(paraString);
		while(matcher.find()){
			byString = matcher.group(0);
			indexby = matcher.start();
		}
		
		//case1: XXXXX from Date1 to Date2
		if(indexfromto > indexby && paraString.endsWith(fromtoString)){	
			matcher = Constants.FROM_PATTERN.matcher(paraString);
			while(matcher.find()){
				startTimeString = matcher.group(0);
			}
			startTime = parseDate(startTimeString);
			
			endOfDescription = indexfromto;
			
			matcher = Constants.TO_PATTERN.matcher(paraString);
			while(matcher.find()){
				endTimeString = matcher.group(0);
			}
			endTime = parseDate(endTimeString);
		}else if(indexby > indexfromto && paraString.endsWith(byString)){
			matcher = Constants.BY_PATTERN.matcher(paraString);
			while(matcher.find()){
				deadlineString = matcher.group(0);
			}
			deadline = parseDate(deadlineString);
			
			endOfDescription = indexby;
		}else{
			endOfDescription = paraString.length();
		}
		
		//Wrong: the description is null!
		if (endOfDescription>0) {
			description = paraString.substring(0,endOfDescription);
		}else {
			throw new CommandWrongArgsException("description can not be empty");
		}
		
		System.out.println(description);
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println(deadline);
		System.out.println(priority);
		
		return new AddCommand(description, startTime, endTime, deadline,
				priority);
		}catch(Exception e){
			throw e;
		}
	}
}
