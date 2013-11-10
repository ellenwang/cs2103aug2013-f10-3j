package com.tobedone.logic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rendezvous.bean.Task;
import com.rendezvous.exception.TaskNotExistException;
import com.rendezvous.service.ToDoListImpl;
import com.rendezvous.utils.Constants;

/**
 * @author A0104167M
 * @version 0.5
 * @since 6-11-2012
 */

public class ToDoListTest {
	ToDoListImpl todo;;

	//@author A0104167M
	@Before
	public void setup() throws IOException, ParseException {
		DOMConfigurator.configure(ToDoListTest.class
				.getResource(Constants.LOG4J_FILE));
		todo = new ToDoListImpl();
		todo.clear();
	}

	//@author A0104167M
	@Test
	public void testSearchByCondition() throws java.text.ParseException,
			TaskNotExistException, IOException {
		Task task1, task2, task3;
		task1 = new Task();
		task1.setContent("project1");
		task1.setPriority("high");
		task2 = new Task();
		task2.setContent("project");
		task2.setPriority("low");
		task3 = new Task();
		task3.setContent("something");
		task3.setPriority("high");
		todo.createTask(task1, false);
		todo.createTask(task2, false);
		todo.createTask(task3, false);
		Map<String, Object> condition = new HashMap<String, Object>();
		List<String> keywordList = new ArrayList<String>();
		keywordList.add("project");
		condition.put(Constants.SEARCH_MAP_KEY_KEYWORD, keywordList);
		condition.put(Constants.SEARCH_MAP_KEY_PRIORITY, "high");
		String actual = todo.searchByCondition(condition).get(0).toString();
		String expected = task1.toString();
		assertTrue(actual.equals(expected));
	}

	//@author A0104167M
	@After
	public void teardown() throws IOException {
		todo.clear();
	}
}