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
	public boolean updateTask (int index, TaskItem updatedTask)
			throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_UPDATE_TASK);
		TaskItem oldTask = allTasks.get(index);
		setLastUpdatedTask(oldTask);
		boolean flag = true;
		allTasks.remove(oldTask);
		flag = allTasks.add(updatedTask);
		setLastCreatedTask(updatedTask);
		storage.store(allTasks);
		return flag;
	}

	@Override
	public boolean deleteTaskById (int index) throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_DELETE_TASK_BYID);
		int before = allTasks.size();
		setLastDeletedTask(allTasks.get(index));
		allTasks.removeElementAt(index);
		int after = allTasks.size();
		if (before-after == 1) {
			return true;
		} else {
			logger.debug(LogMessages.DEBUG_NOT_REMOVED);
			return false;
		}
	}
	
	public boolean deleteTask(TaskItem task) {
		logger.info(LogMessages.INFO_DELETE_TASK);
		boolean flag = allTasks.remove(task);
		setLastDeletedTask(task);
		storage.store(allTasks);
		return flag;
	}
}
