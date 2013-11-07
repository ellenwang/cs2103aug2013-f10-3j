package com.tobedone.parser;

import java.util.Date;

import com.tobedone.command.Command;
import com.tobedone.command.UpdateCommand;
import com.tobedone.parser.utilities.CommandParser;
import com.tobedone.utils.Constants;

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
	
	public Command parse(String paraString) throws Exception{
		int indexofindex = paraString.indexOf(Constants.SPACE);
		int indexoffrom = paraString.lastIndexOf(Constants.REGEX_DATE_FROM_PREFIX);
		int indexofto = paraString.lastIndexOf(Constants.REGEX_DATE_TO_PREFIX);
		int indexofby = paraString.lastIndexOf(Constants.REGEX_DATE_BY_PREFIX);
		int indexofpriority = Constants.NOT_FOUND_INDEX;
		
		index = parseIndex(indexofindex, paraString);
		
		if(paraString.endsWith(Constants.STR_PRI_HIGH)
				||paraString.endsWith(Constants.STR_PRI_NORMAL)
				||paraString.endsWith(Constants.STR_PRI_LOW)){
			indexofpriority = paraString.lastIndexOf(Constants.SPACE)+Constants.ONE_LOOKAHEAD;
		}
		
		if(indexoffrom!= Constants.NOT_FOUND_INDEX 
				&& indexofby == Constants.NOT_FOUND_INDEX){
			if (indexofindex + 1 < indexoffrom - 1) {
				newdescription = paraString.substring(indexofindex + 1,
						indexoffrom - 1);
			}
			String endTimeString;
			String startTimeString;
					
			if (indexofto != Constants.NOT_FOUND_INDEX) {
				startTimeString = paraString.substring(indexoffrom+5,indexofto-Constants.ONE_LOOKAHEAD);
				newstartTime = parseDate(startTimeString);
				if (indexofpriority != Constants.NOT_FOUND_INDEX) {
					endTimeString = paraString.substring(indexofto + 3,
							indexofpriority - 1);
				} else {
					endTimeString = paraString.substring(indexofto + 3);
				}
				newendTime = parseDate(endTimeString);
			}else{
				if (indexofpriority != Constants.NOT_FOUND_INDEX) {
					startTimeString = paraString.substring(indexoffrom + 5,
							indexofpriority - 1);
				} else {
					startTimeString = paraString.substring(indexoffrom + 5);
				}
				newstartTime = parseDate(startTimeString);
			}
		}else if (indexofto != Constants.NOT_FOUND_INDEX
				&&indexofby == Constants.NOT_FOUND_INDEX) {
			if (indexofindex + 1 < indexofto - 1) {
				newdescription = paraString.substring(indexofindex + 1,
						indexofto - 1);
			}
			
			String endTimeString;
			if (indexofpriority != Constants.NOT_FOUND_INDEX) {
				endTimeString = paraString.substring(indexofto + 3,
						indexofpriority - 1);
			} else {
				endTimeString = paraString.substring(indexofto + 3);
			}
			System.out.println(endTimeString);
			newendTime = parseDate(endTimeString);
			
		}else if(indexofby != Constants.NOT_FOUND_INDEX){
			if (indexofindex + 1 < indexofby - 1) {
				newdescription = paraString.substring(indexofindex + 1,
						indexofby - 1);
			}
			String deadlineSting;
			
			if(indexofpriority != Constants.NOT_FOUND_INDEX){
				deadlineSting = paraString.substring(indexofby+3,indexofpriority-1);
			}else{
				deadlineSting = paraString.substring(indexofby+3);
			}
			newdeadline = parseDate(deadlineSting);
			System.out.println(newdeadline);
		}else{
			if(indexofpriority!=Constants.NOT_FOUND_INDEX){
				if(indexofindex+1<indexofpriority-1){
					newdescription = paraString.substring(indexofindex+1,indexofpriority-1);
				}	
			}else{
				newdescription = paraString;
			}
		}

		
	if (indexofpriority != Constants.NOT_FOUND_INDEX) {
		String priorityString = paraString.substring(indexofpriority);
		newpriority = parsePriority(priorityString);
	}
		return new UpdateCommand(index, newdescription, newstartTime, newendTime,newdeadline ,newpriority);
	}
}
