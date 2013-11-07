package com.tobedone.command;

import com.tobedone.taskitem.*;

import java.io.IOException;
import java.util.Vector;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.gsync.GoogleCalendar;
import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

/**
 * @author A0118248
 * @version v0.4
 * @Date 2013-11-6
 */
public class GCalCommand extends Command {
	private GoogleCalendar googleCal = null;

	public GCalCommand() {
		super();
		isUndoable = false;
	}

	Vector<TaskItem> allTasks = toDoService.getAllTasks();

	protected void executeCommand() throws IOException {
		try {
			googleCal = GoogleCalendar.getInstance();
			if (!googleCal.isAuthorized()) {
				googleCal.initAuthenication();
			}
			// CalendarList
			// String chosenCalendarId = googleCal.chooseCalendar();
			String chosenCalendarId = "primary";
			allTasks = googleCal.sync(allTasks, chosenCalendarId);
			// updateTaskIDs();
			toDoService.storeAllTask(allTasks);
			for (TaskItem task : allTasks) {
				aimTasks.add(task);
			}
			feedback = Constants.MESSAGE_SYNC_SUCCESS;
		} catch (ServiceNotAvailableException t) {
			feedback = Constants.SERVER_UNAVAILABLE;
		}
	}
}
