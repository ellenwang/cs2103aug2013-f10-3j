//@author A0105682H
package com.tobedone.logic;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.storage.Storage;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 4-10-2013
 * 
 *        This singleton class handles all commands in command history for undo
 *        and redo purpose. Data structure used: Stack.
 * 
 */

public abstract class ToDoList {

	private static Logger logger = Logger.getLogger(ToDoList.class);

	protected Storage storage;
	protected static Vector<TaskItem> allTasks;
	protected static Vector<TaskItem> matchingTasks;

	// Constructor
	public ToDoList() {
		storage = Storage.getInstance();
		allTasks = new Vector<TaskItem>();
		allTasks = storage.retrieve();
		matchingTasks = new Vector<TaskItem>();
	}

	/**
	 * Abstract method that add a new task into the list of all tasks.
	 * 
	 * @param task
	 *            The new task to be added into the list.
	 * @return Return true if the creation is successful. And otherwise, false.
	 * @throws IOException
	 */
	abstract public boolean createTask(TaskItem task) throws IOException;

	/**
	 * Abstract method that update a task by index in the current list.
	 * 
	 * @param index
	 *            The index of the task to be updated in the current task list.
	 * @param newTask
	 *            The new task that will replace the old task.
	 * @return The old task which is replaced.
	 * @throws TaskNotExistException
	 *             If the task of the index is not found in the list.
	 * @throws IOException
	 */
	abstract public TaskItem updateTask(int index, TaskItem newTask)
			throws TaskNotExistException, IOException;

	/**
	 * Abstract method that deletes a task by index in the current list.
	 * 
	 * @param index
	 *            The index of the task to be updated in the current task list.
	 * @return Return the removed task item.
	 * @throws TaskNotExistException
	 *             If the task of the index is not found in the list.
	 * @throws IOException
	 */
	abstract public TaskItem deleteTaskById(int index)
			throws TaskNotExistException, IOException;

	/**
	 * Abstract method that deletes the exact task item in the task list.
	 * 
	 * @param task
	 *            The task to be deleted.
	 * @return Return true if the deletion is successful and false, otherwise.
	 * @throws TaskNotExistException
	 *             If the task of the index is not found in the list.
	 * @throws IOException
	 */
	abstract public boolean deleteTask(TaskItem task)
			throws TaskNotExistException, IOException;

	/**
	 * Stores the current all task list into the storage.
	 */
	public void storeAllTask() {
		storage.store(allTasks);
	}

	/**
	 * Complete the task by the specified index in the current task list by
	 * changing its status.
	 * 
	 * @param index
	 *            The index of the task to be finished.
	 * @return Return true if the completion is successful and false, otherwise.
	 * @throws IOException
	 * @throws TaskNotExistException
	 *             The task of the specified index cannot be found in the
	 *             current task list.
	 */
	public boolean completeTask(int index) throws IOException,
			TaskNotExistException {
		logger.info(LogMessages.INFO_COMPLETE_TASK);

		TaskItem finishedTaskItem = matchingTasks.get(index);
		finishedTaskItem.setStatus(TaskItem.Status.FINISHED);
		boolean compeletionSuccessful = storage.store(allTasks);
		return compeletionSuccessful;
	}

	/**
	 * Searches the keyword in the task description by going through all the
	 * tasks in the list.
	 * 
	 * @param keyword
	 *            The keyword for searching.
	 * @return Return the list of matching task items with the keyword in their
	 *         descriptions.
	 * @throws TaskNotExistException
	 *             The task of the specified index cannot be found in the
	 *             current task list.
	 */
	public Vector<TaskItem> searchKeyword(String keyword)
			throws TaskNotExistException {
		logger.debug(LogMessages.INFO_SEARCH_KEYWORD);

		Vector<TaskItem> matchingTasks = new Vector<TaskItem>();
		for (int i = 0; i < allTasks.size(); i++) {
			String taskInfo = allTasks.get(i).getDescription().toLowerCase();
			TaskItem currentItem = allTasks.get(i);
			if (taskInfo.contains(keyword)) {
				matchingTasks.add(currentItem);
			}
		}
		return matchingTasks;
	}

	/**
	 * Remove all the tasks in the current task list.
	 * 
	 * @throws IOException
	 */
	public void clear() throws IOException {
		logger.info(LogMessages.INFO_CLEAR);

		allTasks.clear();
		storage.store(allTasks);
	}

	/**
	 * Exit the system.
	 * 
	 * @throws IOException
	 */
	public void exit() throws IOException {
		logger.info(LogMessages.INFO_EXIT);

		System.exit(0);
	}

	/**
	 * Gets all the tasks sorted in default order: priority first, deadline/end
	 * time, start time and descriptions are in alphabetical order.
	 * 
	 * @return The list of all task items sorted in default order.
	 */
	public Vector<TaskItem> getAllTasks() {
		logger.info(LogMessages.INFO_DISPLAY_DEFAULT_ORDER);

		Vector<TaskItem> result = allTasks;
		Collections.sort(result, TaskItem.TaskPriorityComparator);
		return result;
	}

	/**
	 * Assign value to the list of all tasks.
	 * 
	 * @param allTasks
	 *            The new value of the list of all tasks.
	 */
	public void setAllTasks(Vector<TaskItem> allTasks) {
		this.allTasks = allTasks;
	}

	/**
	 * Gets the matching tasks after a command execution.
	 * 
	 * @return The list of matching tasks sorted in default order.
	 */
	public Vector<TaskItem> getMatchingTasks() {
		return matchingTasks;
	}

	/**
	 * Assign value to the list of matching tasks.
	 * 
	 * @param matchingTasks
	 *            The new value of the list of matching tasks.
	 */
	public void setMatchingTasks(Vector<TaskItem> matchingTasks) {
		this.matchingTasks = matchingTasks;
	}
}
