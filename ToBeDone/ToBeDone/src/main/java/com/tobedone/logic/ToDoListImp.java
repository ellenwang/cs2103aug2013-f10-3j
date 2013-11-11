//@author A0105682H
package com.tobedone.logic;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.DeadlinedTask;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TimedTask;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 4-10-2013
 * 
 *        This class extends ToDoList super class and contains the most basic
 *        CRUD operations on the list of all tasks.
 * 
 */

public class ToDoListImp extends ToDoList {

	protected static Logger logger = Logger.getLogger(ToDoListImp.class);

	// Constructor
	public ToDoListImp() {
		super();
	}

	@Override
	/**
	 * Create a new task and store to the storage. Return true if succeeded, false otherwise.
	 */
	public boolean createTask(TaskItem newTask) {
		logger.info(LogMessages.INFO_CREATE_TASK);
		
		allTasks.add(newTask);
		return storage.store(allTasks);
	}

	@Override
	/**
	 * Update a task in the current task list by index. Return the original task.
	 */
	public TaskItem updateTask(int index, TaskItem updatedTask)
			throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UPDATE_TASK);
		
		TaskItem oldTask = matchingTasks.get(index);
		setLastUpdatedTask(oldTask);
		matchingTasks.remove(oldTask);
		boolean deletionSuccessful = deleteTask(oldTask);
		if (deletionSuccessful) {
			createTask(updatedTask);
		}
		setLastCreatedTask(updatedTask);
		return oldTask;
	}

	@Override
	/**
	 * Delete a task in the current task list by index. Return the removed task.
	 */
	public TaskItem deleteTaskById(int index) throws TaskNotExistException,
			IOException {
		logger.info(LogMessages.INFO_DELETE_TASK_BY_ID);
		
		TaskItem task = matchingTasks.remove(index);
		deleteTask(task);
		return task;
	}

	@Override
	/**
	 * Delete the exact task object in the current task list. Return the removed task.
	 */
	public boolean deleteTask(TaskItem task) {
		logger.info(LogMessages.INFO_DELETE_TASK);
		
		boolean removalSuccessful = allTasks.remove(task);
		setLastDeletedTask(task);
		storage.store(allTasks);
		return removalSuccessful;
	}
}
