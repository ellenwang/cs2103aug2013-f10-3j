package com.google.api.services.samples.calendar.cmdline;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Vector;

import com.TaskItem;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

/**
 *
 */
public class GoogleCalendar {
	
	private static SimpleDateFormat simpleDateAndTimeFormat = new SimpleDateFormat(
			"yyyy'-'MM'-'dd'T'HH:mm:ss");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning. Suggested
	 * format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "My App";

	/** Directory to store user credentials. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store/calendar_sample");

	/**
	 * Global instance of the {@link DataStoreFactory}. The best practice is to
	 * make it a single globally shared instance across your application.
	 */
	private static FileDataStoreFactory dataStoreFactory;

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static com.google.api.services.calendar.Calendar client;

	static final java.util.List<Calendar> addedCalendarsUsingBatch = Lists
			.newArrayList();

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY,
				new InputStreamReader(GoogleCalendar.class
						.getResourceAsStream("/client_secrets.json")));

		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret()
						.startsWith("Enter ")) {
			System.out
					.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=calendar "
							+ "into calendar-cmdline-sample/src/main/resources/client_secrets.json");
			System.exit(1);
		}
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, JSON_FACTORY, clientSecrets,
				Collections.singleton(CalendarScopes.CALENDAR))
				.setDataStoreFactory(dataStoreFactory).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	}

	public static Vector<TaskItem> download() {
		Vector<TaskItem> res = null;
		try {
			// initialize the transport
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// initialize the data store factory
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

			// authorization
			Credential credential = authorize();

			// set up global Calendar instance
			client = new com.google.api.services.calendar.Calendar.Builder(
					httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

			// CalendarList
			CalendarList calendarList = client.calendarList().list().execute();

			// Show and select calendar
			CalendarListEntry calendar = displayAndSelectCalendar(calendarList);

			// download from google
			String calendarId = calendar.getId();
			res = downloadFromCalendar(calendarId);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return res;
	}

	public static Vector<TaskItem> downloadFromCalendar(String CalendarID)
			throws Exception {
		Vector<TaskItem> newTasks = new Vector<TaskItem>();
		Events events;
		String pageToken = null;
		do {
			events = client.events().list(CalendarID).setPageToken(pageToken)
					.execute();
			List<Event> items = events.getItems();
			for (Event event : items) {
				long calendarTimeZoneOffset;
				if (event.getStart().getDateTime() != null) {
				    calendarTimeZoneOffset = 1000*60*event.getStart().getDateTime().getTimeZoneShift();
				} else {
					calendarTimeZoneOffset = 1000*60*event.getStart().getDate().getTimeZoneShift();
				}
				long thisTimeZoneOffset = TimeZone.getDefault().getRawOffset();
				long offset = thisTimeZoneOffset - calendarTimeZoneOffset;
				Date startTime = new Date(toDate(event.getStart()).getTime() + offset);
				Date endTime = new Date(toDate(event.getEnd()).getTime() + offset);
				int priority = 2;

				String description = event.getSummary();

				TaskItem newItem = new TaskItem(description, startTime,
						endTime, priority);

				newTasks.add(newItem);
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);
		return newTasks;
	}

	private static Date toDate(EventDateTime e) throws ParseException {
		Date res = new Date();
		if (e.getDateTime() != null) {
			DateTime t = e.getDateTime();
			res = simpleDateAndTimeFormat.parse(t.toString());
		} else if (e.getDate() != null) {
			DateTime t = e.getDate();
			String temp = t.toString();
			res = simpleDateFormat.parse(temp);
		}
		return res;
	}

	public static String upload(Vector<TaskItem> tasks) {
		String feedback = "";
		try {
			// initialize the transport
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// initialize the data store factory
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

			// authorization
			Credential credential = authorize();

			// set up global Calendar instance
			client = new com.google.api.services.calendar.Calendar.Builder(
					httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

			// run commands

			// CalendarList
			CalendarList calendarList = client.calendarList().list().execute();

			// Show and select calendar
			CalendarListEntry calendar = displayAndSelectCalendar(calendarList);
			String calendarId = calendar.getId();
			
			// download calendar tasks
			Vector<TaskItem> calendarTasks = downloadFromCalendar(calendarId);

			// upload to google
			for (TaskItem task : tasks) {
				if (!calendarTasks.contains(task)) {
					Event e = taskToEvent(task);
					
					upload(calendarId, e);
				}
			}

			feedback = "Successfully uploaded to Google Calendar.";
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return feedback;
	}
	

	private static Vector<TaskItem> removeDuplicates(Vector<TaskItem> tasks) {
		Vector<TaskItem> noDuplicates = new Vector<TaskItem>();
		for (TaskItem task : tasks) {
			if (!noDuplicates.contains(task)) {
				noDuplicates.add(task);
			}
		}
		return noDuplicates;
	}

	private static CalendarListEntry displayAndSelectCalendar(
			CalendarList calendarList) {
		showCalendars(calendarList);
		return pickCalendar(calendarList);
	}

	private static Event taskToEvent(TaskItem task) {
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
		return event;
	}

	private static Event newEvent() {
		Event event = new Event();
		event.setSummary("New Event");
		Date startDate = new Date();
		Date endDate = new Date(startDate.getTime() + 3600000);
		DateTime start = new DateTime(startDate, TimeZone.getDefault());
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		event.setEnd(new EventDateTime().setDateTime(end));
		return event;
	}

	private static Event upload(String calendarId, Event event)
			throws IOException {
		Event result = client.events().insert(calendarId, event).execute();
		return result;
	}

	private static CalendarListEntry pickCalendar(CalendarList calendarList) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter calendar index: ");
		String input = sc.nextLine();
		int calendarIndex = Integer.parseInt(input);
		return calendarList.getItems().get(calendarIndex - 1);
	}

	private static void showCalendars(CalendarList calendarList) {
		System.out.println("Please select a calender to synchronize with");
		View.display(calendarList);
	}

	private static void showCalendars() throws IOException {
		System.out.println("Please select a calender to synchronize with");
		CalendarList feed = client.calendarList().list().execute();
		View.display(feed);
	}

	private static void addCalendarsUsingBatch() throws IOException {
		View.header("Add Calendars using Batch");
		BatchRequest batch = client.batch();

		// Create the callback.
		JsonBatchCallback<Calendar> callback = new JsonBatchCallback<Calendar>() {

			@Override
			public void onSuccess(Calendar calendar, HttpHeaders responseHeaders) {
				View.display(calendar);
				addedCalendarsUsingBatch.add(calendar);
			}

			@Override
			public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
				System.out.println("Error Message: " + e.getMessage());
			}
		};

		// Create 2 Calendar Entries to insert.
		Calendar entry1 = new Calendar().setSummary("Calendar for Testing 1");
		client.calendars().insert(entry1).queue(batch, callback);

		Calendar entry2 = new Calendar().setSummary("Calendar for Testing 2");
		client.calendars().insert(entry2).queue(batch, callback);

		batch.execute();
	}

	private static Calendar addCalendar() throws IOException {
		View.header("Add Calendar");
		Calendar entry = new Calendar();
		entry.setSummary("Calendar for Testing 3");
		Calendar result = client.calendars().insert(entry).execute();
		View.display(result);
		return result;
	}

	private static Calendar updateCalendar(Calendar calendar)
			throws IOException {
		View.header("Update Calendar");
		Calendar entry = new Calendar();
		entry.setSummary("Updated Calendar for Testing");
		Calendar result = client.calendars().patch(calendar.getId(), entry)
				.execute();
		View.display(result);
		return result;
	}

	private static void addEvent(Calendar calendar) throws IOException {
		View.header("Add Event");
		Event event = newEvent();
		Event result = client.events().insert(calendar.getId(), event)
				.execute();
		View.display(result);
	}

	private static void showEvents(Calendar calendar) throws IOException {
		View.header("Show Events");
		Events feed = client.events().list(calendar.getId()).execute();
		View.display(feed);
	}

	private static void deleteCalendarsUsingBatch() throws IOException {
		View.header("Delete Calendars Using Batch");
		BatchRequest batch = client.batch();
		for (Calendar calendar : addedCalendarsUsingBatch) {
			client.calendars().delete(calendar.getId())
					.queue(batch, new JsonBatchCallback<Void>() {

						@Override
						public void onSuccess(Void content,
								HttpHeaders responseHeaders) {
							System.out.println("Delete is successful!");
						}

						@Override
						public void onFailure(GoogleJsonError e,
								HttpHeaders responseHeaders) {
							System.out.println("Error Message: "
									+ e.getMessage());
						}
					});
		}

		batch.execute();
	}

	private static void deleteCalendar(Calendar calendar) throws IOException {
		View.header("Delete Calendar");
		client.calendars().delete(calendar.getId()).execute();
	}
}