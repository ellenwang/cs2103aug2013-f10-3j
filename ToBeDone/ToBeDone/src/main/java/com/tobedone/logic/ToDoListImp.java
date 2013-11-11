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
 * @since 01-10-2013
 * 
 *        This class extends ToDoList super class and contains the most basic
 *        CRUD operations on the list of all tasks.
 * 
 */

public class ToDoListImp extends ToDoList {

	protected static Logger logger = Logger.getLogger(ToDoListImp.class);

	//Constructor
	public ToDoListImp() {
		super();
	}

	@Override
	public boolean createTask(TaskItem newTask) {
		logger.info(LogMessages.INFO_CREATE_TASK);
		allTasks.add(newTask);
		return storage.store(allTasks);
	}

	@Override
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
	public TaskItem deleteTaskById(int index) throws TaskNotExistException,
			IOException {
		logger.info(LogMessages.INFO_DELETE_TASK_BYID);
		TaskItem task = matchingTasks.remove(index);
		deleteTask(task);
		return task;
	}

	public boolean deleteTask(TaskItem task) {
		logger.info(LogMessages.INFO_DELETE_TASK);
		boolean flag = allTasks.remove(task);
		setLastDeletedTask(task);
		storage.store(allTasks);
		return flag;
	}
}