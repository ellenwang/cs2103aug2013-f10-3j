package com.tobedone.command;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
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
public class UpdateCommandAtd {
	private Storage storage;
	private ToDoList toDoList;

	@Before
	public void before() {
		storage = Storage.getInstance();
		storage.changeToTestFile();
		storage.store(new Vector<TaskItem>());
		toDoList = new ToDoListImp();
	}

	@After
	public void after() {
		storage.store(new Vector<TaskItem>());
		storage.changeToMainFile();
	}

	@Test
	public void testUpdateDescription() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		String newDescription = "test floating task change";

		Command updateCommand = new UpdateCommand(0, newDescription, null,
				null, null, 0);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new FloatingTask(newDescription, priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testUpdatePriority() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		int newPriority = 2;

		Command updateCommand = new UpdateCommand(0, null, null, null, null,
				newPriority);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new FloatingTask(description, newPriority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testUpdateStartTimeAndEndTime() throws Exception {
		String description = "test timed task";
		String startTimeString = "10:00,12-12-2013";
		Date startTime = Constants.simpleDateFormat.parse(startTimeString);
		String endTimeString = "10:00,13-12-2013";
		Date endTime = Constants.simpleDateFormat.parse(endTimeString);
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		String newStartTimeString = "10:00,2-12-2013";
		Date newStartTime = Constants.simpleDateFormat
				.parse(newStartTimeString);
		String newEndTimeString = "10:00,24-12-2013";
		Date newEndTime = Constants.simpleDateFormat.parse(newEndTimeString);

		Command updateCommand = new UpdateCommand(0, null, newStartTime,
				newEndTime, null, 0);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new TimedTask(description, newStartTime,
				newEndTime, priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testUpdateFloatingToDeadlined() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		String newDescription = "test deadlined task";
		String newDeadlineString = "8:00,1-1-2014";
		Date newDeadline = Constants.simpleDateFormat.parse(newDeadlineString);

		Command updateCommand = new UpdateCommand(0, newDescription, null,
				null, newDeadline, 0);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new DeadlinedTask(newDescription, newDeadline,
				priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testUpdateFloatingToTimed() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		String newDescription = "test timed task";
		String newStartTimeString = "10:00,24-12-2013";
		Date newStartTime = Constants.simpleDateFormat
				.parse(newStartTimeString);
		String newEndTimeString = "23:00,1-1-2013";
		Date newEndTime = Constants.simpleDateFormat.parse(newEndTimeString);

		Command updateCommand = new UpdateCommand(0, newDescription, newStartTime,
				newEndTime, null, 0);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new TimedTask(newDescription, newStartTime,
				newEndTime, priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testUpdateDeadlinedToTimed() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		String deadlineString = "10:00,10-6-2014";
		Date deadline = Constants.simpleDateFormat
				.parse(deadlineString);
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();

		String newDescription = "test timed task";
		String newStartTimeString = "10:00,1-10-2014";
		Date newStartTime = Constants.simpleDateFormat
				.parse(newStartTimeString);
		String newEndTimeString = "23:00,11-11-2015";
		Date newEndTime = Constants.simpleDateFormat.parse(newEndTimeString);

		Command updateCommand = new UpdateCommand(0, newDescription, newStartTime,
				newEndTime, null, 0);
		updateCommand.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new TimedTask(newDescription, newStartTime,
				newEndTime, priority);
		assertTrue(task.equals(expectedTask));
	}
}
