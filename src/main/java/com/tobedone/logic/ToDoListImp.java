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

public class ToDoListImp extends ToDoList {

	protected static Logger logger = Logger.getLogger(ToDoListImp.class);

	public ToDoListImp() {
		super();
	}

	// @author A0105682H
	@Override
	public boolean createTask(TaskItem newTask) {
		logger.info(LogMessages.INFO_CREATE_TASK);
		boolean flag = allTasks.add(newTask);
		setLastCreatedTask(newTask);
		storage.store(allTasks);
		return flag;
	}

	@Override
	public TaskItem updateTask(int index, TaskItem updatedTask)
			throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UPDATE_TASK);
		TaskItem oldTask = matchingTasks.get(index);
		setLastUpdatedTask(oldTask);
		boolean flag;
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
