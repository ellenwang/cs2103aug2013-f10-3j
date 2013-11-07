package test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.thread.Timeout.Task;

import com.tobedone.command.AddCommand;
import com.tobedone.command.Command;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.parser.Parser;
import com.tobedone.parser.utils.CommandParser;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * 
 * @author A0118441M
 * 
 */
public class AddCommandAtd {
	private Storage storage;
	private ToDoList toDoList;
	private CommandParser commandParser;

	@Before
	public void before() {
		storage = Storage.getInstance();
		storage.changeToTestFile();
		storage.clear();
		toDoList = new ToDoListImp();
		commandParser = CommandParser.getInstance();
	}

	@After
	public void after() {
		storage.clear();
		storage.changeToMainFile();
	}

	@Test
	public void testAddFloatingTask() throws Exception {
		String description = "test floating task";
		String commandString = "add " + description;
		Parser parser = Parser.getInstance();
		Command command = parser.parseCommand(commandString);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new FloatingTask(description,
				Constants.DEFAULT_PRIORITY);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testAddDeadlinedTask() throws Exception {
		String description = "test deadlined task";
		String deadline = "10:00 24-12-2013";
		String commandString = "add " + description + " by " + deadline;
		Parser parser = Parser.getInstance();
		Command command = parser.parseCommand(commandString);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		Date deadlineDate = commandParser.parseDate(deadline);
		TaskItem expectedTask = new DeadlinedTask(description, deadlineDate,
				Constants.DEFAULT_PRIORITY);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testAddTimedTask() throws Exception {
		String description = "test deadlined task";
		String startTime = "10:00 24-12-2013";
		String endTime = "11:00 24-12-2013";
		String commandString = "add " + description + " from " + startTime
				+ " to " + endTime;
		Parser parser = Parser.getInstance();
		Command command = parser.parseCommand(commandString);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		Date startTimeDate = commandParser.parseDate(startTime);
		Date endTimeDate = commandParser.parseDate(endTime);
		TaskItem expectedTask = new TimedTask(description, startTimeDate,
				endTimeDate, Constants.DEFAULT_PRIORITY);
		assertTrue(task.equals(expectedTask));
	}
}
