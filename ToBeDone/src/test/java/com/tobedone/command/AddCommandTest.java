package com.tobedone.command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.rendezvous.bean.Task;

/**
 * @author A0104167M
 * @version 0.5
 * @since 6-11-2012
 * 
 */
public class AddCommandTest extends Command {
	Command add;

	// @author A0104167M
	@Before
	public void setUp() throws Exception {
		toDoService.clear();
		gCalService.clear();
	}

	// @author A0104167M
	@After
	public void tearDown() throws Exception {
		toDoService.clear();
		gCalService.clear();
	}

	// @author A0104167M
	@Test
	public void testExecuteCommand() throws IOException, ParseException,
			java.text.ParseException {
		String commandString = "add \"FLOATING TASK\"";
		String commandString2 = "add \"DEADLINE TASK\">12:00 01-11-2013";
		String commandString3 = "add \"TIMED TASK\">12:00 01-11-2013 13:00 11-12-2013";
		add = new AddCommand(commandString);
		add.execute();
		List<Task> list = toDoService.getAllTask(0);
		String actual = list.get(0).getContent();
		String expected = "FLOATING TASK";
		String expected2 = "DEADLINE TASK";
		String expected3 = "TIMED TASK";
		assertEquals(expected, actual);
	}

	// @author A0104167M
	@Test
	public void testUndo() throws IOException, ParseException,
			java.text.ParseException {
		String commandString = "add \"TIMED TASK\"";
		add = new AddCommand(commandString);
		add.execute();
		List<Task> list = toDoService.getAllTask(0);
		String actual = list.get(0).getContent();
		String expected = "TIMED TASK";
		assertEquals(expected, actual);
		add.undo();
		List<Task> list2 = toDoService.getAllTask(0);
		int actualListSize = list2.size();
		int expectedListSize = 0;
		assertTrue(expectedListSize == actualListSize);
	}

	@Override
	protected void executeCommand() {
	}

}
