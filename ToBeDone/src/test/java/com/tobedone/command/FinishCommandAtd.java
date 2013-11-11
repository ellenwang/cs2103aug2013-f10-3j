package com.tobedone.command;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;

/**
 * 
 * @author A0118441M
 * @version 0.5
 * @since 11-11-2013
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
	public void testFinishAnUnfinishedTask() throws Exception {
		String description = "test unfinished task";
		Date startTime = null;
		Date endTime = null;
		Date deadline = null;
		int priority = 3;
		Command addCommand = new AddCommand(description, startTime, endTime,
				deadline, priority);
		addCommand.execute();
		
		Command finishCommand = new FinishCommand(0);
		finishCommand.execute();
		
		Vector<TaskItem> tasks = toDoList.getAllTasks();
		TaskItem task = tasks.get(0);
		Status expectedStatus = Status.FINISHED;
		assertEquals(task.getStatus(), expectedStatus);
	}
}
