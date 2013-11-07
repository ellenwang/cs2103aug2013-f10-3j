package com.tobedone.gsync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tobedone.taskitem.*;

/**
 * @author A0118248
 * @version v0.4
 * @Date 2013-11-6
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
			DateTime start = new DateTime(startTime, TimeZone.getDefault());
			event.setStart(new EventDateTime().setDateTime(start));
			Date endTime = ((TimedTask) task).getEndTime();
			DateTime end = new DateTime(endTime, TimeZone.getDefault());
			event.setEnd(new EventDateTime().setDateTime(end));
			
		}else if( task instanceof DeadlinedTask){
			Date currentDate = new Date();
			DateTime start = new DateTime(currentDate, TimeZone.getDefault());
			event.setStart(new EventDateTime().setDateTime(start));
			
			Date endTime = ((DeadlinedTask) task).getEndTime();
			DateTime end = new DateTime(endTime, TimeZone.getDefault());
			event.setEnd(new EventDateTime().setDateTime(end));
		}else{
			Date currentDate = new Date();
			DateTime start = new DateTime(currentDate, TimeZone.getDefault());
			event.setStart(new EventDateTime().setDateTime(start));
			
			Date endDate = new Date(currentDate.getTime() + 24 * 7 * 3600000);
			DateTime end = new DateTime(endDate, TimeZone.getDefault());
			event.setEnd(new EventDateTime().setDateTime(end));

		} 
		event.setColorId(getColor(task));
		return event;
	}

	/**
	 * @author A0118248
	 * @param Event
	 * @return TaskItem Convert a google calendar task to a local task.
	 */
	public TaskItem eventToTask(Event event) throws ParseException {
		Date startTime = new Date(toDate(event.getStart()).getTime());
		Date endTime = new Date(toDate(event.getEnd()).getTime());

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
	 * @author A0118248
	 * @param Event
	 * @return int Get the priority from the color of google calendar task.
	 */
	private static int getPriority(Event e) {
		if(e.getColorId() == null){
			return 1;
		}
		if (e.getColorId().equals("10")) {
			return 2;
		} else if (e.getColorId().equals("11")) {
			return 3;
		} else {
			return 1;
		}
	}

	/**
	 * @author A0118248
	 * @param TaskItem
	 * @return String Get the color from the priority of local task.
	 */
	private static String getColor(TaskItem e) {
		if (e.getPriority() == 1) {
			return "9";
		} else if (e.getPriority() == 2) {
			return "10";
		} else if (e.getPriority() == 3) {
			return "11";
		}
		return null;
	}

	/**
	 * @author A0118248
	 * @param EventDateTime
	 * @return Date Convert the date and time of a google calendar task to the
	 *         date and time of a local task.
	 */
	private static Date toDate(EventDateTime e) throws ParseException {
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
			long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
			res = simpleDateFormatOfGoogle.parse(t.toString());
			res = new Date(res.getTime() + offset);
		}
		return res;
	}

	public Vector<TaskItem> removeDuplicate(Vector<TaskItem> tasks) {
		Vector<TaskItem> noDuplicates = new Vector<TaskItem>();
		for (TaskItem task : tasks) {
			if (!noDuplicates.contains(task)) {
				noDuplicates.add(task);
			}
		}
		return noDuplicates;
	}

	public boolean isExisted(TaskItem t, Vector<TaskItem> tasks) {
		return tasks.contains(t);
	}
}
