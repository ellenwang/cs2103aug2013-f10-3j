package com.tobedone.logic;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.rendezvous.bean.Task;
import com.rendezvous.exception.TaskNotExistException;
import com.rendezvous.service.ToDoList;
import com.rendezvous.service.ToDoListImpl;
import com.rendezvous.utils.Constants;

/**
 * @author A0104167M
 * @version 0.5
 * @since 6-11-2012
 * 
 */
public class ToDoListImplTest {
	private ToDoList todo;
	private List<Task> taskList = new ArrayList<Task>();
	private static long sequenceNum = 0;

	// @author A0104167M
	@Before
	public void setup() throws IOException, ParseException,
			java.text.ParseException {
		todo = new ToDoListImpl();
		todo.clear();
		sequenceNum = 0;
		taskList.addAll(generateNewTaskList());
		for (Task task : taskList) {
			todo.createTask(task, false);
		}
	}

	// @author A0104167M
	@Test
	public void testGetAllTasks() throws IOException, ParseException,
			java.text.ParseException {
		List<Task> allTaskList = todo.getAllTask(0);
		String actual = "";
		for (Task t : allTaskList) {
			actual += t.toString() + "\n";
		}
		String expected = getDisplayAllTasks();
		assertTrue(actual.equals(expected));
	}

	// @author A0104167M
	@Test
	public void testDeleteTask() throws IOException, ParseException,
			java.text.ParseException, TaskNotExistException {
		long[] ids = { 1, 5, sequenceNum };
		for (int i = 0; i < ids.length; i++) {
			todo.deleteTaskById(ids[i]);
			List<Task> temp = new ArrayList<Task>();
			temp.addAll(taskList);
			for (Task task : taskList) {
				if (task.getId() == ids[i]) {
					temp.remove(task);
				}
			}
			taskList = temp;
		}
		String expected = getDisplayAllTasks();
		List<Task> allTaskList = todo.getAllTask(0);
		String actual = "";
		for (Task t : allTaskList) {
			actual += t.toString() + "\n";
		}
		assertTrue(actual.equals(expected));
	}

	// @author A0104167M
	@Test
	public void testUpdateTask() throws IOException, ParseException,
			java.text.ParseException, TaskNotExistException {
		long testId = sequenceNum / 2;
		Task newTask = taskList.get((int) testId - 1);
		newTask.setContent("Call WeiKeng");
		List<String> tagList = new ArrayList<String>();
		tagList.add("work");
		newTask.setTagList(tagList);
		newTask.setPriority(Constants.PRI_LOW);
		todo.updateTaskById(testId, newTask);
		String expected = getDisplayAllTasks();
		List<Task> allTaskList = todo.getAllTask(0);
		String actual = "";
		for (Task t : allTaskList) {
			actual += t.toString() + "\n";
		}
		assertTrue(actual.equals(expected));
	}

	// @author A0104167M
	@After
	public void teardown() throws IOException {
		todo.clear();
		todo.exit();
	}

	// @author A0104167M
	private String getDisplayAllTasks() {
		StringBuilder sb = new StringBuilder();
		for (Task task : taskList) {
			sb.append(task.toString() + "\n");
		}
		return sb.toString();
	}

	// @author A0104167M
	private List<Task> generateNewTaskList() throws java.text.ParseException {
		taskList = new ArrayList<Task>();
		for (int i = 0; i < 10; i++) {
			sequenceNum++;
			String time = "10:11 11-10-2013";
			DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date date = df.parse(time);
			Task task = new Task();
			// task.setId(sequenceNum);
			task.setDone(false);
			task.setContent("pay my phone bill" + sequenceNum);
			// task.setStartTime(date);
			// task.setEndTime(date);
			task.setPriority(Constants.PRI_HIGH);
			List<String> tagList = new ArrayList<String>();
			tagList.add("work");
			tagList.add("home");
			task.setTagList(tagList);
			taskList.add(task);
		}
		return taskList;
	}
}