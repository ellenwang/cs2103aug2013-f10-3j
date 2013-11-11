package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.gsync.GoogleCalendar;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

/**
 * @author A0118248A
 * @version v0.5
 * @Date 2013-11-10
 */
public class SyncDownCommand extends Command {
	private GoogleCalendar googleCal = null;

	public SyncDownCommand() {
		super();
		isUndoable = false;
	}

	Vector<TaskItem> allTasks = toDoService.getAllTasks();
	//@Author: A0118248A
	protected void executeCommand() throws IOException {
		try {
			googleCal = GoogleCalendar.getInstance();
			try{
				if (!googleCal.isAuthorized()) {
					googleCal.initAuthenication();
				}
			}catch(Exception e){
				feedback = Constants.MSG_GCAL_NOT_AUTHORIZED;
			}			
			String chosenCalendarId = Constants.DEFAULT_CALENDAR;
			aimTasks = googleCal.updateLocal(allTasks, chosenCalendarId);
			toDoService.storeAllTask();
			if(aimTasks.size()==Constants.ZERO){
				feedback = Constants.MESSAGE_NO_UPDATE_TO_LOCAL;
				aimTasks.addAll(allTasks);
			}else{
				feedback = Constants.MESSAGE_DOWNLOAD_SUCCESS;
			}			
		} catch (ServiceNotAvailableException t) {
			feedback = Constants.SERVER_UNAVAILABLE;
		}
	}
}
