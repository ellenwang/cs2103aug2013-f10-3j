//@author A0105682H
package com.tobedone.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

public class SearchCommandAtd {
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
	public void searchExistingKeyword() throws IOException,
			TaskNotExistException, ParseException {
		// add timed task
		String description1 = "Graphic user interface settled software";
		String startTimeString1 = "10:00,11-12-2013";
		Date startTime1 = Constants.simpleDateFormat.parse(startTimeString1);
		String endTimeString1 = "16:00,24-12-2013";
		Date endTime1 = Constants.simpleDateFormat.parse(endTimeString1);
		Date deadline1 = null;
		int priority1 = 1;
		TimedTask task1 = new TimedTask(description1, startTime1, endTime1,
				priority1);
		Command command1 = new AddCommand(description1, startTime1, endTime1,
				deadline1, priority1);
		command1.execute();

		String description2 = "user experience check meeting";
		String startTimeString2 = "14:00,10-12-2013";
		Date startTime2 = Constants.simpleDateFormat.parse(startTimeString2);
		String endTimeString2 = "16:00,10-12-2013";
		Date endTime2 = Constants.simpleDateFormat.parse(endTimeString2);
		Date deadline2 = null;
		int priority2 = 3;
		TimedTask task2 = new TimedTask(description2, startTime2, endTime2,
				priority2);
		Command command2 = new AddCommand(description2, startTime2, endTime2,
				deadline2, priority2);
		command2.execute();

		// add deadlined task
		String description3 = "user interview report";
		Date startTime3 = null;
		Date endTime3 = null;
		String deadlineString3 = "23:59, 10-12-2013";
		Date deadline3 = Constants.simpleDateFormat.parse(deadlineString3);
		int priority3 = 1;
		DeadlinedTask task3 = new DeadlinedTask(description3, deadline3,
				priority3);
		Command command3 = new AddCommand(description3, startTime3, endTime3,
				deadline3, priority3);
		command3.execute();

		// add a task
		String description4 = "Final examinations software";
		Date startTime4 = null;
		Date endTime4 = null;
		String deadlineString4 = "14:00, 09-12-2013";
		Date deadline4 = Constants.simpleDateFormat.parse(deadlineString3);
		int priority4 = 1;
		DeadlinedTask task4 = new DeadlinedTask(description4, deadline4,
				priority4);
		Command command4 = new AddCommand(description4, startTime4, endTime4,
				deadline4, priority4);
		command4.execute();

		String description5 = "Reading week user interview";
		Date startTime5 = null;
		Date endTime5 = null;
		Date deadline5 = null;
		int priority5 = 1;
		FloatingTask task5 = new FloatingTask(description5, priority5);
		Command command5 = new AddCommand(description5, startTime5, endTime5,
				deadline5, priority5);
		command5.execute();

		// add floating task
		String description6 = "Final examinations revision SoftWARE";
		Date startTime6 = null;
		Date endTime6 = null;
		Date deadline6 = null;
		int priority6 = 2;
		FloatingTask task6 = new FloatingTask(description6, priority6);
		Command command6 = new AddCommand(description6, startTime6, endTime6,
				deadline6, priority6);
		command6.execute();

		Command searchCommand = new SearchCommand("user");
		searchCommand.execute();
		Vector<TaskItem> tasks = toDoList.getMatchingTasks();
		Vector<TaskItem> expected = new Vector<TaskItem>();
		expected.add(task2);
		expected.add(task3);
		expected.add(task1);
		expected.add(task5);
		Collections.sort(tasks, TaskItem.TaskItemComparator);
		Collections.sort(expected, TaskItem.TaskItemComparator);
		boolean isEqual = true;
		for (int i = 0; i < tasks.size(); i++) {
			if (!tasks.get(i).equals(expected.get(i))) {
				isEqual = false;
			}
		}
		assertTrue(isEqual);
	}

	public void searchNonExistingKeyword() throws IOException,
			TaskNotExistException {
		Command searchCommand1 = new SearchCommand("apple");
		searchCommand1.execute();
		Vector<TaskItem> result = toDoList.getMatchingTasks();
		assertEquals(result.size(), 0);
	}
}
