package com.tobedone.command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * 
 * @author A0118441M
 * 
 */
public class RemoveCommandAtd {
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
	public void removeFirstTaskInListOfSingleItem() throws IOException, TaskNotExistException {
		// add a task
		String description = "test floating task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command command = new AddCommand(description, startTime, endTime,
				deadline, priority);
		command.execute();
		
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		
		// task list contains 1 task
		assertEquals(tasks.size(), 1);
		
		// remove the first task
		command = new RemoveCommand(0);
		command.execute();
		
		// task list is empty
		assertEquals(tasks.size(), 0);
	}
	
	@Test
	public void removeTaskInListOfMultipleTasks() throws ParseException,
			IOException, TaskNotExistException {
		// add a task
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

		// add a task
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
		
		// task list contains 2 tasks
		assertEquals(tasks.size(), 2);
		
		// remove the second task
		Command command = new RemoveCommand(1);
		command.execute();
		
		// task list contains 1 task
		assertEquals(tasks.size(), 1);
		
		// first task still remains in the list
		TaskItem expectedTask = new TimedTask(description1, startTime1, endTime1,
				priority1);
		assertTrue(expectedTask.equals(tasks.get(0)));
	}
}
