//@author A0105682H
package com.tobedone.logic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tobedone.command.AddCommand;
import com.tobedone.command.Command;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.FloatingTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.Constants;

/**
 * Junit test for ToDoListImp class. The validity and boundary situations are
 * handled and tested by the Command classes. So just check the basic
 * functionalities.
 * 
 * @author A0105682H
 * 
 */
public class ToDoListImpAtd {
	private Storage storage;
	private ToDoList toDoList;
	private Vector<TaskItem> expected;
	private Vector<TaskItem> result;

	@Before
	public void before() {
		storage = Storage.getInstance();
		storage.changeToTestFile();
		storage.store(new Vector<TaskItem>());
		toDoList = new ToDoListImp();
		expected = new Vector<TaskItem>();
	}

	@After
	public void after() {
		storage.store(new Vector<TaskItem>());
		storage.changeToMainFile();
	}

	@Test
	public void init() throws IOException, ParseException {
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
		toDoList.createTask(task1);

		String description2 = "user experience check meeting";
		String startTimeString2 = "14:00,10-12-2013";
		Date startTime2 = Constants.simpleDateFormat.parse(startTimeString2);
		String endTimeString2 = "16:00,10-12-2013";
		Date endTime2 = Constants.simpleDateFormat.parse(endTimeString2);
		Date deadline2 = null;
		int priority2 = 3;
		TimedTask task2 = new TimedTask(description2, startTime2, endTime2,
				priority2);
		toDoList.createTask(task2);

		// add deadlined task
		String description3 = "user interview report";
		Date startTime3 = null;
		Date endTime3 = null;
		String deadlineString3 = "23:59, 10-12-2013";
		Date deadline3 = Constants.simpleDateFormat.parse(deadlineString3);
		int priority3 = 1;
		DeadlinedTask task3 = new DeadlinedTask(description3, endTime3,
				priority3);
		toDoList.createTask(task3);

		String description4 = "Final examinations software";
		Date startTime4 = null;
		Date endTime4 = null;
		String deadlineString4 = "14:00, 09-12-2013";
		Date deadline4 = Constants.simpleDateFormat.parse(deadlineString3);
		int priority4 = 1;
		DeadlinedTask task4 = new DeadlinedTask(description4, endTime4,
				priority4);
		toDoList.createTask(task4);

		// add floating task
		String description5 = "Reading week user interview";
		Date startTime5 = null;
		Date endTime5 = null;
		Date deadline5 = null;
		int priority5 = 1;
		FloatingTask task5 = new FloatingTask(description5, priority5);
		toDoList.createTask(task5);

		String description6 = "Final examinations revision SoftWARE";
		Date startTime6 = null;
		Date endTime6 = null;
		Date deadline6 = null;
		int priority6 = 2;
		FloatingTask task6 = new FloatingTask(description6, priority6);
		toDoList.createTask(task6);

		expected.add(task1);
		expected.add(task2);
		expected.add(task3);
		expected.add(task4);
		expected.add(task5);
		expected.add(task6);
	}

	// Tests the createTask() method
	public void createTaskTest() throws IOException, TaskNotExistException,
			ParseException {
		result = toDoList.getMatchingTasks();
		assertTrue(this.compareVector(result, expected));

	}

	// Tests the updateTask() method
	public void updateTaskTest() throws ParseException, TaskNotExistException,
			IOException {
		String description5 = "Reading week user interview";
		String deadlineString5 = "18:00, 12-12-2014";
		int priority5 = 1;
		Date newdeadline5 = Constants.simpleDateFormat.parse(deadlineString5);
		DeadlinedTask newtask5 = new DeadlinedTask(description5, newdeadline5,
				priority5);
		toDoList.updateTask(4, newtask5);
		result = toDoList.getMatchingTasks();
		expected.set(4, newtask5);
		assertTrue(this.compareVector(result, expected));

	}

	// Tests the deleteTaskByIdTest() method
	public void deleteTaskByIdTest() throws TaskNotExistException, IOException {
		expected.remove(3);
		toDoList.deleteTaskById(3);
		result = toDoList.getMatchingTasks();
		assertTrue(this.compareVector(result, expected));
	}

	// Tests the deleteTask() method
	public void deleteTaskTest() throws ParseException, TaskNotExistException,
			IOException {
		String description1 = "Graphic user interface settled software";
		String startTimeString1 = "10:00,11-12-2013";
		Date startTime1 = Constants.simpleDateFormat.parse(startTimeString1);
		String endTimeString1 = "16:00,24-12-2013";
		Date endTime1 = Constants.simpleDateFormat.parse(endTimeString1);
		Date deadline1 = null;
		int priority1 = 1;
		TimedTask task1 = new TimedTask(description1, startTime1, endTime1,
				priority1);
		toDoList.deleteTask(task1);
		result = toDoList.getMatchingTasks();
		expected.remove(task1);
		assertTrue(this.compareVector(result, expected));
	}

	/**
	 * Compares two vectors of TaskItem
	 * @param result
	 * 			The result vector list of TaskItem after the execution of the command.
	 * @param expected
	 * 			The expected vector list of the TaskItem.
	 * @return
	 * 			Return true if two vectors are equal and false, otherwise.
	 */
	public boolean compareVector(Vector<TaskItem> result,
			Vector<TaskItem> expected) {
		boolean isEqual = true;
		for (int i = 0; i < result.size(); i++) {
			if (!result.get(i).equals(expected.get(i))) {
				isEqual = false;
			}
		}
		return isEqual;
	}
}
