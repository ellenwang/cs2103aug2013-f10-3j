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
public class FinishCommandAtd {
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
}
