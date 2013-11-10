package com.tobedone.command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rendezvous.bean.Task;
import com.rendezvous.service.ToDoList;
import com.rendezvous.service.ToDoListImpl;
import com.rendezvous.service.gcal.GCalToDoImpl;

/**
 * @author A0087510J
 * @version 0.5
 * @since 6-11-2012
 * 
 */
public class ClearCommandTest {
	Command clear;
	ToDoList toDoService;
	GCalToDoImpl gCalService;

	// @author A0087510J
	@Before
	public void setUp() throws Exception {
		clear = null;
		gCalService = new GCalToDoImpl();
	}

	// @author A0087510J
	@After
	public void tearDown() throws Exception {
		toDoService = new ToDoListImpl();
		toDoService.clear();
		gCalService.clear();
	}

	// @author A0087510J
	@Test
	public final void testExecuteCommand() throws IOException, ParseException,
			java.text.ParseException {
		String addCommandString1 = "add \"TIMED TASK\"";
		String addCommandString2 = "add \"DEADLINE TASK\"";
		String addCommandString3 = "add \"FLOATING TASK\"";
		new AddCommand(addCommandString1).execute();
		new AddCommand(addCommandString2).execute();
		new AddCommand(addCommandString3).execute();
		String clearCommandString = "clear";
		clear = new ClearCommand(clearCommandString);
		clear.execute();
		toDoService = new ToDoListImpl();
		List<Task> list = toDoService.getAllTask(0);
		int actualListSize = list.size();
		int expectedListSize = 0;
		assertTrue(expectedListSize == actualListSize);
	}

	// @author A0087510J
	@Test
	public final void testUndo() throws IOException, ParseException,
			java.text.ParseException {
		String addCommandString1 = "add \"TIMED TASK\"";
		String addCommandString2 = "add \"DEADLINE TASK\"";
		String addCommandString3 = "add \"FLOATING TASK\"";
		new AddCommand(addCommandString1).execute();
		new AddCommand(addCommandString2).execute();
		new AddCommand(addCommandString3).execute();
		String clearCommandString = "clear";
		clear = new ClearCommand(clearCommandString);
		clear.execute();
		toDoService = new ToDoListImpl();
		List<Task> list = toDoService.getAllTask(0);
		int actualListSize = list.size();
		int expectedListSize = 0;
		assertTrue(expectedListSize == actualListSize);

		clear.undo();
		toDoService = new ToDoListImpl();
		List<Task> list2 = toDoService.getAllTask(0);
		actualListSize = list2.size();
		expectedListSize = 3;
		assertTrue(expectedListSize == actualListSize);
	}
}