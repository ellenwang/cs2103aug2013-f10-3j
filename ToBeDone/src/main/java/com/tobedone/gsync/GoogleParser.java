package com.tobedone.gsync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * @author A0118248A
 * @version v0.5
 * @Date 2013-11-10
 */

public class GoogleParser {
	
	private static SimpleDateFormat simpleDateAndTimeFormatOfGoogle = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
	private static SimpleDateFormat simpleDateFormatOfGoogle = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	/**
	 * @param TaskItem
	 * @return Event Convert a local task to google calendar task.
	 */
	public Event taskToEvent(TaskItem task) {
		Event event = new Event();
		event.setSummary(task.getDescription());
		
		if (task instanceof TimedTask) {
			Date startTime = ((TimedTask) task).getStartTime();
			event.setStart(toEventDateTime(startTime));
			
			Date endTime = ((TimedTask) task).getEndTime();
			event.setEnd(toEventDateTime(endTime));
		}else if( task instanceof DeadlinedTask){
			Date currentDate = new Date();
			event.setStart(toEventDateTime(currentDate));
			
			Date endTime = ((DeadlinedTask) task).getEndTime();
			event.setEnd(toEventDateTime(endTime));
		}else{
			Date currentDate = new Date();
			event.setStart(toEventDateTime(currentDate));
			
			Date endDate = new Date(currentDate.getTime() + 24 * 7 * 3600000);
			event.setEnd(toEventDateTime(endDate));

		} 
		event.setColorId(getColor(task));
		return event;
	}

	/**
	 * @author A0118248A
	 * @param Event
	 * @return TaskItem Convert a google calendar task to a local task.
	 */
	public TaskItem eventToTask(Event event) throws ParseException {
		Date startTime = new Date(toDate(event.getStart(),false).getTime());
		Date endTime = new Date(toDate(event.getEnd(),true).getTime());

		int priority = getPriority(event);

		String description = event.getSummary();
		TaskItem newItem = new TimedTask(description, startTime, endTime,
				priority);
		Date current = new Date();
		if (endTime.getTime() < current.getTime()) {
			newItem.setStatus(TaskItem.Status.FINISHED);
		}
		return newItem;
	}

	/**
	 * @author A0118248A
	 * @param Event
	 * @return int Get the priority from the color of google calendar task.
	 */
	private static int getPriority(Event e) {
		if(e.getColorId() == null){
			return Constants.INT_PRI_LOW;
		}
		if (e.getColorId().equals(Constants.STRING_PRI_MED)) {
			return Constants.INT_PRI_NORMAL;
		} else if (e.getColorId().equals(Constants.STRING_PRI_HIGH)) {
			return Constants.INT_PRI_HIGH;
		} else {
			return Constants.INT_PRI_LOW;
		}
	}

	/**
	 * @author A0118248A
	 * @param TaskItem
	 * @return String Get the color from the priority of local task.
	 */
	private static String getColor(TaskItem e) {
		if (e.getPriority() == Constants.INT_PRI_LOW) {
			return Constants.STRING_PRI_LOW;
		} else if (e.getPriority() == 2) {
			return Constants.STRING_PRI_MED;
		} else if (e.getPriority() == 3) {
			return Constants.STRING_PRI_HIGH;
		}
		return null;
	}

	/**
	 * @author A0118248A
	 * @param EventDateTime
	 * @return Date Convert the date and time of a google calendar task to the
	 *         date and time of a local task.
	 */
	public static Date toDate(EventDateTime e, boolean st) throws ParseException {
		long calendarTimeZoneOffset;
		long thisTimeZoneOffset = TimeZone.getDefault().getRawOffset();

		Date res = new Date();
		if (e.getDateTime() != null) {
			calendarTimeZoneOffset = 1000 * 60 * e.getDateTime()
					.getTimeZoneShift();
			DateTime t = e.getDateTime();
			long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
			res = simpleDateAndTimeFormatOfGoogle.parse(t.toString());
			res = new Date(res.getTime() + offset);
		} else if (e.getDate() != null) {
			calendarTimeZoneOffset = 1000 * 60 * e.getDate().getTimeZoneShift();
			DateTime t = e.getDate();
			//long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
			res = simpleDateFormatOfGoogle.parse(t.toString());
			if(st == false){
				res = new Date(res.getTime());
			}else{
				res = new Date(res.getTime());
			}
			
		}
		return res;
	}
	
	public EventDateTime toEventDateTime(Date e){
		DateTime t = new DateTime(e, TimeZone.getDefault());
		return new EventDateTime().setDateTime(t);
	}
	
}
