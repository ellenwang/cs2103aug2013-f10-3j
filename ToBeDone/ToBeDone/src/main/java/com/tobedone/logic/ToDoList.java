package com.tobedone.logic;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.ToDoList;

public abstract class ToDoList {
	private static Logger logger = Logger.getLogger(ToDoList.class);
	
	protected Storage storage;
	protected static Vector<TaskItem> allTasks = new Vector<TaskItem>();
	protected static Vector<TaskItem> matchingTasks;
	private TaskItem lastCreatedTask ;
	private TaskItem lastDeletedTask ;
	private TaskItem lastUpdatedTask ;
	

	public ToDoList() {
		storage = Storage.getInstance(); // getInstance(); singleton
		allTasks = storage.retrieve();
		matchingTasks = new Vector<TaskItem>();
	}
	
	abstract public boolean createTask(TaskItem task) throws IOException;

	abstract public TaskItem updateTask(int index, TaskItem newTask)
			throws TaskNotExistException, IOException;

	abstract public TaskItem deleteTaskById(int index)
			throws TaskNotExistException, IOException;
	
	abstract public boolean deleteTask(TaskItem task) 
			throws TaskNotExistException, IOException;

	public Vector<TaskItem> getAllTasks() {
		Vector<TaskItem> result = allTasks;
		logger.info(LogMessages.INFO_DISPLAY_PRIORITY);
		Collections.sort(result, TaskItem.TaskPriorityComparator);
		return result;
	}

	public void storeAllTask (Vector<TaskItem> newTasks) {
		storage.store(allTasks);
	}
	
	public Vector<TaskItem> searchKeyword (String keyword)
			throws TaskNotExistException {
		logger.debug(LogMessages.INFO_SEARCH_KEYWORD);
		Vector<TaskItem> matchingTasks = new Vector<TaskItem>();
		for (int i = 0; i < allTasks.size(); i++) {
			String taskInfo = allTasks.get(i).getDescription();
			TaskItem currentItem = allTasks.get(i);
			if (taskInfo.contains(keyword)) {
				matchingTasks.add(currentItem);
			}
		}
		return matchingTasks;
	}

	public void clear() throws IOException {
		// TODO Auto-generated method stub
		logger.info(LogMessages.INFO_CLEAR);
		allTasks.clear();
		storage.store(allTasks);
	}
	
	public boolean completeTask(int index) throws IOException,TaskNotExistException {
		
		logger.info(LogMessages.INFO_COMPLETE);
		TaskItem finishedTaskItem = matchingTasks.get(index);
		setLastUpdatedTask(finishedTaskItem);
		finishedTaskItem.setStatus(TaskItem.Status.FINISHED);
		storage.store(allTasks);

		return true;
	}
	
	public void exit() throws IOException {
		logger.info(LogMessages.INFO_EXIT);
		System.exit(0);
	}

	public void setAllTasks(Vector<TaskItem> allTasks) {
		this.allTasks = allTasks;
	}
	public TaskItem getLastUpdatedTask() {
		return lastUpdatedTask;
	}
	public TaskItem getLastCreatedTask() {
		return lastCreatedTask;
	}
	public TaskItem getLastDeletedTask() {
		return lastDeletedTask;
	}
	public void setLastCreatedTask(TaskItem task){
		lastCreatedTask = task;
	}
	public void setLastDeletedTask(TaskItem task){
		lastDeletedTask = task;
	}
	public void setLastUpdatedTask(TaskItem task){
		lastDeletedTask = task;
	}

	public Vector<TaskItem> getMatchingTasks() {
		return matchingTasks;
	}

	public void setMatchingTasks(Vector<TaskItem> matchingTasks) {
		this.matchingTasks = matchingTasks;
	}
}
