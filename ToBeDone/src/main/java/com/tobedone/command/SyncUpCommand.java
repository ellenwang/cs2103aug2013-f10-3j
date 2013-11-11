package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.gsync.GoogleCalendar;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

/**
 * @author A0118248A
 * @version v0.5
 * @Date 2013-11-10
 */
public class SyncUpCommand extends Command {
	private GoogleCalendar googleCal = null;

	public SyncUpCommand() {
		super();
		isUndoable = false;
	}

	Vector<TaskItem> allTasks = toDoService.getAllTasks();
	//@Author: A0118248A
	protected void executeCommand() throws IOException {
		googleCal = GoogleCalendar.getInstance();
		try{
			if (!googleCal.isAuthorized()) {
				googleCal.initAuthenication();
			}
		}catch(Exception e){
			feedback = Constants.MSG_GCAL_NOT_AUTHORIZED;
		}		
		String chosenCalendarId = Constants.DEFAULT_CALENDAR;
		aimTasks = allTasks;
		googleCal.updateGcal(chosenCalendarId, allTasks);		
		feedback = Constants.MESSAGE_UPLOAD_SUCCESS;
	}
}
