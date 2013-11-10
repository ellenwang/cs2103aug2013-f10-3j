package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 6-11-2013
 * 
 *        This class handles remove command and undo service for this command.
 * 
 */
public class RemoveCommand extends Command {
	private int index;
	private Vector<TaskItem> matchingTasks;
	private TaskItem taskToDelete;

	// @author A0105682H
	public RemoveCommand(int index) {
		super();
		this.index = index;
		this.matchingTasks = toDoService.getMatchingTasks();
		isUndoable = true;
		taskToDelete = matchingTasks.get(index);
		executionSuccessful = false;
	}

	// @author A0105682H
	@Override
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_REMOVE);
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}
		if (index < 0 || index > matchingTasks.size()) {
			feedback = "";
		} else {
			boolean deleteSuccessful = toDoService.deleteTask(taskToDelete);
			if (deleteSuccessful) {
				feedback = Constants.MSG_DELETE_SUCCESSFUL;
				aimTasks.remove(taskToDelete);
				executionSuccessful = true;
			} else {
				feedback = Constants.MSG_DELETE_FAILED;
			}
		}
	}

	// @author A0105682H
	@Override
	public void undo() {
		logger.info(LogMessages.INFO_UNDO_ACTION);
		try {
			toDoService.createTask(taskToDelete);
			feedback = Constants.MSG_REMOVE_UNDO;
			aimTasks.add(taskToDelete);
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);
			feedback = Constants.MSG_UNDO_FAILED;
		}
	}
}
