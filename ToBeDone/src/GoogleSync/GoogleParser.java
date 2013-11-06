package googlesync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import com.TaskItem;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleParser {
	
	private static SimpleDateFormat simpleDateAndTimeFormatOfGoogle = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
	private static SimpleDateFormat simpleDateFormatOfGoogle = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	public Event taskToEvent(TaskItem task) {
		Event event = new Event();
		event.setSummary(task.getDescription());
		Date startTime = task.getStartTime();
		if (startTime != null) {
			DateTime start = new DateTime(startTime, TimeZone.getDefault());
			event.setStart(new EventDateTime().setDateTime(start));
		} else {
			Date date = new Date();
			DateTime start = new DateTime(date, TimeZone.getDefault());
			event.setStart(new EventDateTime().setDateTime(start));
		}
		Date endTime = task.getEndTime();
		if (endTime != null) {
			DateTime end = new DateTime(endTime, TimeZone.getDefault());
			event.setEnd(new EventDateTime().setDateTime(end));
		} else {
			Date currentDate = new Date();
			Date endDate = new Date(currentDate.getTime() + 24 * 7 * 3600000);
			DateTime end = new DateTime(endDate, TimeZone.getDefault());
			event.setEnd(new EventDateTime().setDateTime(end));
		}
		event.setColorId(getColor(task));
		return event;
	}
	public TaskItem eventToTask(Event event) throws ParseException{
		Date startTime = new Date(toDate(event.getStart()).getTime());
		Date endTime = new Date(toDate(event.getEnd()).getTime());
		
		int priority = getPriority(event) ;

		String description = event.getSummary();
		TaskItem newItem = new TaskItem(description, startTime,
				endTime, priority);
		Date current =new Date();
		if(endTime.getTime() < current.getTime()){
			newItem.setStatus(TaskItem.Status.FINISHED);
		}
		return newItem;
	}
	
	private static int getPriority(Event e){
		if(e.getColorId().equals("10")){
			return 2;
		}else if(e.getColorId().equals("11")){
			return 3;
		}else {
			return 1;
		}
	}
	private static String getColor(TaskItem e){
		if(e.getPriority() == 1){
			return "9";
		}else if(e.getPriority() == 2){
			return "10";
		}else if(e.getPriority() == 3){
			return "11";
		}
		return null;
	}

	private static Date toDate(EventDateTime e) throws ParseException {
		long calendarTimeZoneOffset;
		long thisTimeZoneOffset = TimeZone.getDefault().getRawOffset();
		
		Date res = new Date();
		if (e.getDateTime() != null) {
			calendarTimeZoneOffset = 1000*60*e.getDateTime().getTimeZoneShift();
			DateTime t = e.getDateTime();
			long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
			res = simpleDateAndTimeFormatOfGoogle.parse(t.toString());
			res = new Date(res.getTime() + offset);
		} else if (e.getDate() != null) {
			calendarTimeZoneOffset = 1000*60*e.getDate().getTimeZoneShift();
			DateTime t = e.getDate();
			long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
			res = simpleDateFormatOfGoogle.parse(t.toString());
			res = new Date(res.getTime()+offset);
		}
		return res;
	}

}
