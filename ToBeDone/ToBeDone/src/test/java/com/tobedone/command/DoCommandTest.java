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
 * @author A0087836M
 * @version 0.5
 * @since 6-11-2012
 * 
 */
public class DoCommandTest {
	Command doCommand;
	ToDoList toDoService;
	GCalToDoImpl gCalService;

	// @author A0087836M
	@Before
	public void setUp() throws Exception {
		doCommand = null;
		toDoService = new ToDoListImpl();
		gCalService = new GCalToDoImpl();
	}

	// @author A0087836M
	@After
	public void tearDown() throws Exception {
		toDoService = new ToDoListImpl();
		toDoService.clear();
		gCalService.clear();
	}

	// @author A0087836M
	@Test
	public final void testExecuteCommand() throws IOException, ParseException,
			java.text.ParseException {
		String addCommandString1 = "add \"FIRST TASK\"";
		String addCommandString2 = "add \"SECOND TASK\"";
		new AddCommand(addCommandString1).execute();
		new AddCommand(addCommandString2).execute();
		toDoService = new ToDoListImpl();
		List<Task> list = toDoService.getAllTask(1);
		Task expected = list.get(0);
		String doCommandString = "do 1";
		doCommand = new DoCommand(doCommandString);
		doCommand.execute();
		toDoService = new ToDoListImpl();
		toDoService.switchDataFile();
		List<Task> archived = toDoService.getAllTask(1);
		Task actual = archived.get(0);
		assertTrue(expected.toString().equals(actual.toString()));

	}

	// @author A0087836M
	@Test
	public final void testUndo() throws IOException, ParseException,
			java.text.ParseException {
		String addCommandString1 = "add \"FIRST TASK\"";
		String addCommandString2 = "add \"SECOND TASK\"";
		new AddCommand(addCommandString1).execute();
		new AddCommand(addCommandString2).execute();
		toDoService = new ToDoListImpl();
		List<Task> list = toDoService.getAllTask(0);
		Task expected = list.get(0);
		String doCommandString = "do 1";
		doCommand = new DoCommand(doCommandString);
		doCommand.execute();
		doCommand.undo();
		List<Task> list2 = toDoService.getAllTask(0);
		Task actual = list2.get(0);
		assertTrue(expected.toString().equals(actual.toString()));
	}
}