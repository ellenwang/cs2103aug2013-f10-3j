package com.tobedone.gcal;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;


import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.google.api.services.calendar.model.Event;
import com.tobedone.exception.ServiceNotAvailableException;
import com.tobedone.gsync.GoogleCalendar;
import com.tobedone.gsync.GoogleParser;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;


public class testGCal {
	GoogleCalendar test;
	GoogleParser gParser;
	ToDoListImp toDoService;
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		test = GoogleCalendar.getInstance();
		gParser = new GoogleParser();
		toDoService = new ToDoListImp();
	}


	@Test
	public final void testAddTask() throws ParseException, IOException,
			org.json.simple.parser.ParseException, ServiceNotAvailableException {
		TimedTask s = new TimedTask("test",new Date(),new Date(),3);
		
		//s.setDescription("addTest");
		//s.setStartTime(new Date());
		//s.setPriority(3);

		test.initAuthenication();
		
		test.updateLocal(toDoService.getAllTasks(),"zhuang1992@gmail.com");
		
		int actualSize = toDoService.getAllTasks().size();
		int expectedSize = 2;
		assertTrue(actualSize == expectedSize);
	}
}
