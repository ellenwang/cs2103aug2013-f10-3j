package com.tobedone.command;

import static org.junit.Assert.*;

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
 * @version 0.5
 * @since 11-11-2013
 * 
 */
public class AddCommandAtd {
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
	public void testAddNullTask() throws Exception {
		String description = null;
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 0;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		assertTrue(tasks.isEmpty());
	}
	
	@Test
	public void testAddTaskTwice() throws Exception {
		String description = "duplicate tasks";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 2;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		assertEquals(tasks.size(), 1);
	}
	
	@Test
	public void testAddFloatingTask() throws Exception {
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new FloatingTask(description, priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testAddDeadlinedTask() throws Exception {
		String description = "test deadlined task";
		Date startTime = null;
		Date endTime = null;
		String deadlineString = "12:33,12-11-2013";
		Date deadline = Constants.simpleDateFormat.parse(deadlineString);
		int priority = 1;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new DeadlinedTask(description, deadline,
				priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testAddTimedTask() throws Exception {
		String description = "test timed task";
		String startTimeString = "10:00,11-7-2013";
		Date startTime = Constants.simpleDateFormat.parse(startTimeString);
		String endTimeString = "16:00,24-12-2013";
		Date endTime = Constants.simpleDateFormat.parse(endTimeString);
		Date deadline = null;
		int priority = 1;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		TaskItem expectedTask = new TimedTask(description, startTime, endTime,
				priority);
		assertTrue(task.equals(expectedTask));
	}

	@Test
	public void testMultipleTasks() throws Exception {
		String description1 = "test timed task";
		String startTimeString1 = "10:00,11-7-2013";
		Date startTime1 = Constants.simpleDateFormat.parse(startTimeString1);
		String endTimeString1 = "16:00,24-12-2013";
		Date endTime1 = Constants.simpleDateFormat.parse(endTimeString1);
		Date deadline1 = null;
		int priority1 = 1;
		Command command1 = new AddCommand(description1, startTime1, endTime1,
				deadline1, priority1);
		command1.execute();

		String description2 = "test timed task";
		String startTimeString2 = "14:00,11-10-2013";
		Date startTime2 = Constants.simpleDateFormat.parse(startTimeString2);
		String endTimeString2 = "12:00,24-11-2013";
		Date endTime2 = Constants.simpleDateFormat.parse(endTimeString2);
		Date deadline2 = null;
		int priority2 = 3;
		Command command2 = new AddCommand(description2, startTime2, endTime2,
				deadline2, priority2);
		command2.execute();

		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task1 = tasks.get(0);
		TaskItem expectedTask1 = new TimedTask(description1, startTime1,
				endTime1, priority1);
		assertTrue(task1.equals(expectedTask1));

		TaskItem task2 = tasks.get(1);
		TaskItem expectedTask2 = new TimedTask(description2, startTime2,
				endTime2, priority2);
		assertTrue(task2.equals(expectedTask2));
	}
}
