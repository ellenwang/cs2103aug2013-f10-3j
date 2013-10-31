package Storage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import TaskItem.TaskItem;


public class StorageAtd {
	public static final String MESSAGE_PARSING_ERROR = "Parsing error";
	
	public static final String TESTING_FILE_NAME = "test.txt";
	public static final File TEST_FILE = new File(TESTING_FILE_NAME);
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM','HH:mmyyyy");

	// change the file to a testfile to not overwrite data of ToBeDone
	static {
		Storage.changeFile(TEST_FILE);
	}
	
	@Test
	public void storeAndRetrieveEmptyList() {
		Vector<TaskItem> tasks = new Vector<TaskItem>();
		Vector<TaskItem> receivedTasks = new Vector<TaskItem>();
		
		// Store a list of empty task items
		tasks.clear();
		Storage.store(tasks);
		
		// Retrieve an empty list
		receivedTasks = Storage.retrieve();
		
		
		assertEquals(receivedTasks.size(),tasks.size());
	}
	
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
		TaskItem.Status status = TaskItem.Status.FINISHED;
		
		TaskItem task1 = new TaskItem(description, startTime, endTime, priority, status);
		tasks.add(task1);
		Storage.store(tasks);

		// Retrieve a single task
		receivedTasks = Storage.retrieve();
		assertEquals(receivedTasks.size(),tasks.size());
		assertTrue(receivedTasks.get(0).equals(task1));
	}
	
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
		TaskItem.Status status = TaskItem.Status.FINISHED;
		TaskItem task1 = new TaskItem(description, startTime, endTime, priority, status);
		tasks.add(task1);
		Storage.store(tasks);
		
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
		status = TaskItem.Status.FINISHED;
		TaskItem task2 = new TaskItem(description, startTime, endTime, priority, status);
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
		status = TaskItem.Status.FINISHED;
		TaskItem task3 = new TaskItem(description, startTime, endTime, priority, status);
		tasks.add(task3);
		
		Storage.store(tasks);
		
		// retrieve a list of multiple tasks
		receivedTasks = Storage.retrieve();
		assertEquals(receivedTasks.size(),tasks.size());
		assertTrue(receivedTasks.get(0).equals(task1));
		assertTrue(receivedTasks.get(1).equals(task2));
		assertTrue(receivedTasks.get(2).equals(task3));
	}
}
