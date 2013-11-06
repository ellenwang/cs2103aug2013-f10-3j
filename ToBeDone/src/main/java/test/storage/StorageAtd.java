package test.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utilities.Constants;

/**
 * @author A0118441M
 *
 */
public class StorageAtd {
	private static final String MESSAGE_PARSING_ERROR = "Parsing error";

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM','HH:mmyyyy");

	private static Storage storage = Storage.getInstance();
	
	@Before
	public void before() {
		storage.changeToTestFile();
		storage.clear();
	}
	
	@After
	public void after() {
		storage.clear();
		storage.changeToMainFile();
	}

	@Test
	public void storeNullList() {
		Vector<TaskItem> tasks = null;
		String feedback = storage.store(tasks);
		assertTrue(feedback.equals(Constants.MESSAGE_TASK_LIST_NULL));
	}

	/**
	 * Tests the storage and retrieve methods for the empty partition case
	 */
	@Test
	public void storeAndRetrieveEmptyList() {
		Vector<TaskItem> tasks = new Vector<TaskItem>();
		Vector<TaskItem> receivedTasks = new Vector<TaskItem>();

		// Store a list of empty task items
		tasks.clear();
		String feedback = storage.store(tasks);
		assertTrue(feedback.equals(Constants.MESSAGE_STORE_SUCCESSFUL));

		// Retrieve an empty list
		receivedTasks = storage.retrieve();

		assertEquals(receivedTasks.size(), tasks.size());
	}

	/**
	 * Tests the storage and retrieve methods for the single task partition case
	 */
	@Test
	public void storeAndRetrieveListOfSingleTask() {
		Vector<TaskItem> tasks = new Vector<TaskItem>();
		Vector<TaskItem> receivedTasks = new Vector<TaskItem>();

		// Store a list of one task
		String description = "TestDescription";
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = simpleDateFormat.parse("25/10,12:002013");
			endTime = simpleDateFormat.parse("25/10,13:002013");
		} catch (ParseException pe) {
			System.err.println(MESSAGE_PARSING_ERROR);
		}
		int priority = 2;
		TaskItem task1 = new TimedTask(description, startTime, endTime,
				priority);
		tasks.add(task1);
		String feedback = storage.store(tasks);
		assertTrue(feedback.equals(Constants.MESSAGE_STORE_SUCCESSFUL));

		// Retrieve a single task
		receivedTasks = storage.retrieve();
		assertEquals(receivedTasks.size(), tasks.size());
		assertTrue(receivedTasks.get(0).equals(task1));
	}

	/**
	 * Tests the storage and retrieve method by storing and retrieving multiple
	 * tasks.
	 */
	@Test
	public void storeAndRetrieveListOfMultipleTasks() {
		Vector<TaskItem> tasks = new Vector<TaskItem>();
		Vector<TaskItem> receivedTasks = new Vector<TaskItem>();

		// Store a list of multiple tasks
		String description = "TestDescription";
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = simpleDateFormat.parse("25/10,13:002013");
			endTime = simpleDateFormat.parse("25/10,15:002013");
		} catch (ParseException pe) {
			System.err.println(MESSAGE_PARSING_ERROR);
		}
		int priority = 1;
		TaskItem task1 = new TimedTask(description, startTime, endTime,
				priority);
		tasks.add(task1);

		description = "TestDescription";
		startTime = null;
		endTime = null;
		try {
			startTime = simpleDateFormat.parse("25/10,12:002013");
			endTime = simpleDateFormat.parse("25/10,13:002013");
		} catch (ParseException pe) {
			System.err.println(MESSAGE_PARSING_ERROR);
		}
		priority = 3;
		TaskItem task2 = new TimedTask(description, startTime, endTime,
				priority);
		tasks.add(task2);

		description = "TestDescription";
		startTime = null;
		endTime = null;
		try {
			startTime = simpleDateFormat.parse("24/10,12:002013");
			endTime = simpleDateFormat.parse("26/10,10:002013");
		} catch (ParseException pe) {
			System.err.println(MESSAGE_PARSING_ERROR);
		}
		priority = 2;
		TaskItem task3 = new TimedTask(description, startTime, endTime,
				priority);
		tasks.add(task3);

		String feedback = storage.store(tasks);
		assertTrue(feedback.equals(Constants.MESSAGE_STORE_SUCCESSFUL));

		// retrieve a list of multiple tasks
		receivedTasks = storage.retrieve();
		assertEquals(receivedTasks.size(), tasks.size());
		assertTrue(receivedTasks.get(0).equals(task1));
		assertTrue(receivedTasks.get(1).equals(task2));
		assertTrue(receivedTasks.get(2).equals(task3));
	}
}
