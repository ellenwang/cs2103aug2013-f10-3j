package com.tobedone.gsync;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;


import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.google.api.services.calendar.model.Event;
import com.tobedone.command.Command;
import com.tobedone.command.*;
import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.gsync.GoogleCalendar;
import com.tobedone.gsync.GoogleParser;
import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * @author A0118248A
 * @version v0.5
 * @Date 2013-11-10
 */
//author A0118248A
public class testGCal {
	GoogleParser gParser;
	ToDoListImp toDoService;
	CommandExecuteResult result;
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		gParser = new GoogleParser();
		toDoService = new ToDoListImp();
	}

	@Test
	public final void testDownload() throws ParseException, IOException,
			org.json.simple.parser.ParseException, ServiceNotAvailableException, TaskNotExistException {
		Command command = new SyncDownCommand();
		command.execute();
		assertTrue(command.getFeedback().equals(Constants.MESSAGE_NO_UPDATE_TO_LOCAL));
	}
	@Test
	public final void testUpload() throws IOException, TaskNotExistException{
		Command command = new SyncUpCommand();
		command.execute();
		assertTrue(command.getFeedback().equals(Constants.MESSAGE_UPLOAD_SUCCESS));
		
	}
}
