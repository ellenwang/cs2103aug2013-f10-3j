package com.tobedone.gsync;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * @version v0.5
 * @Date 2013-11-10
 */
public class GoogleCalendar {
	private static GoogleCalendar singleton = null;
	GoogleParser gParser = null;
	private boolean isAuthorized;

	// @Author A0118248A
	GoogleCalendar() {
		gParser = new GoogleParser();
		isAuthorized = false;
	}

	// @Author A0118248A
	public static GoogleCalendar getInstance() {
		if (singleton == null) {
			singleton = new GoogleCalendar();
		}
		return singleton;
	}

	private static final String APPLICATION_NAME = "2BeDone";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store");
	private static FileDataStoreFactory dataStoreFactory;
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static com.google.api.services.calendar.Calendar client;

	static final java.util.List<Calendar> addedCalendarsUsingBatch = Lists
			.newArrayList();

	// @Author A0118248A
	public boolean isAuthorized() {
		return isAuthorized;
	}

	/**
	 * @Author A0118248A Authorizes the installed application to access user's
	 *         protected data. returns a credential to google calendar
	 * */
	private static Credential authorize() throws Exception {
		// load client secrets
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets
				.load(JSON_FACTORY,
						new InputStreamReader(
								GoogleCalendar.class
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
		if (res == null) {
			throw new ServiceNotAvailableException(Constants.SERVER_UNAVAILABLE);
		}
		return res;
	}

	/**
	 * @Author A0118248A To initialize the authentication.
	 * */
	public void initAuthenication() {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Credential credential = authorize();
			client = new com.google.api.services.calendar.Calendar.Builder(
					httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
			this.isAuthorized = true;
		} catch (Exception e) {
			this.isAuthorized = false;
		}
	}

	public String chooseCalendar() throws IOException {
		// CalendarList
		CalendarList calendarList = client.calendarList().list().execute();

		// Show and select calendar
		CalendarListEntry calendar = displayAndSelectCalendar(calendarList);
		return calendar.getId();
	}

	/**
	 * @Author A0118248A
	 * @Date 2013-11310 Get tasks from Google calendar and import them to local
	 *       tasks. return the vector of newly added tasks.
	 */
	public Vector<TaskItem> updateLocal(Vector<TaskItem> allTasks,
			String calendarId) throws ServiceNotAvailableException {
		Vector<TaskItem> newTasks = new Vector<TaskItem>();
		try {
			Events events;
			String pageToken = null;
			do {
				events = client.events().list(calendarId)
						.setPageToken(pageToken).execute();
				List<Event> items = events.getItems();
				for (Event event : items) {
					boolean isUpdated = false;
					boolean isFloating = false;
					for (TaskItem task : allTasks) {
						if (task.getDescription().equals(event.getSummary())) {
							if (task instanceof TimedTask) {
								((TimedTask) task).setStartTime(GoogleParser
										.toDate(event.getStart(), false));
								((TimedTask) task).setEndTime(GoogleParser
										.toDate(event.getEnd(), true));
								if (((TimedTask) task).getEndTime().getTime() < new Date()
										.getTime()) {
									((TimedTask) task)
											.setStatus(Status.FINISHED);
								}
								isUpdated = true;
								break;
							} else if (task instanceof DeadlinedTask) {
								((DeadlinedTask) task).setEndTime(GoogleParser
										.toDate(event.getEnd(), true));
								if (((DeadlinedTask) task).getEndTime()
										.getTime() < new Date().getTime()) {
									((DeadlinedTask) task)
											.setStatus(Status.FINISHED);
								}
								isUpdated = true;
								break;
							} else {
								isFloating = true;
							}
						}
					}
					if (!isUpdated && !isFloating) {
						TaskItem newItem = gParser.eventToTask(event);
						allTasks.add(newItem);
						newTasks.add(newItem);
					}
				}
				pageToken = events.getNextPageToken();
			} while (pageToken != null);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return newTasks;
	}

	/**
	 * @Author A0118248A Upload the local tasks to Google Calendar, creating new
	 *         events or updating existed events in Google Calendar.
	 * */
	public void updateGcal(String calendarId, Vector<TaskItem> allTasks) {
		Vector<TaskItem> tasksFromGoogle = new Vector<TaskItem>();
		try {
			Events events;
			String pageToken = null;
			do {
				events = client.events().list(calendarId)
						.setPageToken(pageToken).execute();
				List<Event> items = events.getItems();
				for (Event event : items) {
					TaskItem taskToUpdate = null;
					for (TaskItem task : allTasks) {
						if (task.getDescription().equals(event.getSummary())) {
							client.events().delete(calendarId, event.getId())
									.execute();
							taskToUpdate = task;
							break;
						}
					}
					if (taskToUpdate != null) {
						upload(calendarId, gParser.taskToEvent(taskToUpdate));
						tasksFromGoogle.add(taskToUpdate);
					} else {
						tasksFromGoogle.add(gParser.eventToTask(event));
						client.events().delete(calendarId, event.getId())
								.execute(); // delete from google
					}
				}
				pageToken = events.getNextPageToken();
			} while (pageToken != null);

			for (TaskItem lTask : allTasks) {
				boolean isExisted = false;
				for (TaskItem gTask : tasksFromGoogle) {
					if (gTask.getDescription().equals(lTask.getDescription())) {
						isExisted = true;
					}
				}
				if (!isExisted) {
					upload(calendarId, gParser.taskToEvent(lTask));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Author A0118248A Clear the primary google calendar.
	 * */
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

	private static Event upload(String calendarId, Event event)
			throws IOException {
		Event result = client.events().insert(calendarId, event).execute();
		return result;
	}

	private static CalendarListEntry pickCalendar(CalendarList calendarList)
			throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print(calendarList.getItems().size() + 1);
		System.out.println(". Create a new Calendar");
		System.out.print("Enter calendar index: ");
		String input = sc.nextLine();
		sc.close();
		int calendarIndex = Integer.parseInt(input);
		if (calendarIndex == calendarList.getItems().size() + 1) {
			String newCalID = addCalendar();
			calendarList = client.calendarList().list().execute();
			for (CalendarListEntry e : calendarList.getItems()) {
				if (e.getId().equals(newCalID)) {
					return e;
				}
			}
			return null;
		} else {
			return calendarList.getItems().get(calendarIndex - 1);
		}
	}

	private static void showCalendars(CalendarList calendarList) {
		System.out.println("Please select a calender to synchronize with");
		View.display(calendarList);
	}

	private static String addCalendar() throws IOException {
		Calendar entry = new Calendar();
		System.out.println("Please input the name of the new calendar:");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		entry.setSummary(input);
		Calendar result = client.calendars().insert(entry).execute();
		sc.close();
		return result.getId();
	}
}