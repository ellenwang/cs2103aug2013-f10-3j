package com.tobedone.gsync;

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
import com.google.api.client.http.javanet.NetHttpTransport;
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
import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;



/**
 * 
 * @version v0.4
 * @Date 2013-11-6
 */
public class GoogleCalendar {
	private static GoogleCalendar singleton = null;
	private static Storage storage = null;
	GoogleParser gParser = null;
	private boolean isAuthorized;
	private String feedback = null;
	GoogleCalendar(){
		storage = Storage.getInstance();
		gParser = new GoogleParser();
		isAuthorized = false;
	}
	
	public static GoogleCalendar getInstance(){
		if(singleton == null){
			singleton = new GoogleCalendar();
		}
		return singleton;
	}
	
	private static final String APPLICATION_NAME = "2BeDone";

	/** Directory to store user credentials. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
	 "./config");
	
	/**
	 * Global instance of the {@link DataStoreFactory}. The best practice is to
	 * make it a single globally shared instance across your application.
	 */
	private static FileDataStoreFactory dataStoreFactory;
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static com.google.api.services.calendar.Calendar client;

	static final java.util.List<Calendar> addedCalendarsUsingBatch = Lists
			.newArrayList();
	
	public boolean isAuthorized(){
		return isAuthorized;
	}
	
	/*
	 * @Author A0118248
	 * 
	 * Authorizes the installed application to access user's protected data.
	 * */
	private static Credential authorize() throws Exception {
		// load client secrets
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
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
		Credential res = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		if(res == null){
			throw new ServiceNotAvailableException(Constants.SERVER_UNAVAILABLE);
		}
		return res;		
	}
	public void initAuthenication(){
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Credential credential = authorize();
			client = new com.google.api.services.calendar.Calendar.Builder(
					httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
			this.isAuthorized = true;
		} catch (Exception e) {
			this.feedback = Constants.MSG_GCAL_NOT_AUTHORIZED;
			this.isAuthorized = false;
		}
	}
	public String chooseCalendar() throws IOException{
		// CalendarList
					CalendarList calendarList = client.calendarList().list().execute();

		// Show and select calendar
		CalendarListEntry calendar = displayAndSelectCalendar(calendarList);
		return calendar.getId();
	}
	public Vector<TaskItem> sync(Vector<TaskItem> allTasks, String calendarId) throws ServiceNotAvailableException{
		Vector<TaskItem> tasksFromGoogle = null;
		try {
			// download from google
			//String calendarId = calendar.getId();
			tasksFromGoogle = downloadFromCalendar(calendarId);
			allTasks.addAll(tasksFromGoogle);
			allTasks = gParser.removeDuplicate(allTasks);
			
			for (TaskItem task : allTasks) {
				if(!gParser.isExisted(task, tasksFromGoogle)){
					Event e = gParser.taskToEvent(task);
					upload(calendarId, e);
				}
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		} 
		return allTasks;
	}
	
	private  Vector<TaskItem> downloadFromCalendar(String CalendarID)
			throws Exception {
		Vector<TaskItem> newTasks = new Vector<TaskItem>();
		Events events;
		String pageToken = null;
		do {
			events = client.events().list(CalendarID).setPageToken(pageToken)
					.execute();
			List<Event> items = events.getItems();
			for (Event event : items) {				
				TaskItem newItem = gParser.eventToTask(event);
				newTasks.add(newItem);
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);
		return newTasks;
	}
	
	public String clearGoogle(Vector<TaskItem> tasks) {
		String feedback = "";
		try {
			// initialize the data store factory
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

			// CalendarList
			CalendarList calendarList = client.calendarList().list().execute();

			// Show and select calendar
			CalendarListEntry calendar = displayAndSelectCalendar(calendarList);
			String calendarId = calendar.getId();
			
			client.calendars().clear(calendarId).execute();
		
			feedback = "Successfully clear the calendar.";
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "Clear failed";
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return feedback;
	}
	
	public static CalendarListEntry displayAndSelectCalendar(
			CalendarList calendarList) throws IOException {
		showCalendars(calendarList);
		return pickCalendar(calendarList);
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

	public static Event upload(String calendarId, Event event)
			throws IOException {
		Event result = client.events().insert(calendarId, event).execute();
		return result;
	}

	private static CalendarListEntry pickCalendar(CalendarList calendarList) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print(calendarList.getItems().size()+1);
		System.out.println(". Create a new Calendar");
		System.out.print("Enter calendar index: ");
		String input = sc.nextLine();
		int calendarIndex = Integer.parseInt(input);
		if(calendarIndex == calendarList.getItems().size()+1){
			String newCalID = addCalendar();
			calendarList = client.calendarList().list().execute();
			for(CalendarListEntry e : calendarList.getItems()){
				if(e.getId().equals(newCalID)){
					return e;
				}
			}
			return null;
		}else{
			return calendarList.getItems().get(calendarIndex - 1);
		}		
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

	private static String addCalendar() throws IOException {
		//View.header("Add Calendar");
		Calendar entry = new Calendar();
		System.out.println("Please input the name of the new calendar:");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		entry.setSummary(input);
		Calendar result = client.calendars().insert(entry).execute();
		//View.display(result);
		return result.getId();
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