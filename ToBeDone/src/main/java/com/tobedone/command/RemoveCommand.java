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

	// @author A0105682H
	public RemoveCommand(int index) {
		super();
		this.index = index;
		this.matchingTasks = toDoService.getMatchingTasks();
		isUndoable = true;
	}

	// @author A0105682H
	@Override
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_REMOVE);
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}
		if (index < 0 || index > matchingTasks.size()) {
			feedback = "";
		} else {
			TaskItem deletedTask = toDoService.deleteTaskById(index);
			if (deletedTask != null) {
				feedback = Constants.MSG_DELETE_SUCCESSFUL;
				aimTasks.remove(deletedTask);
			} else {
				feedback = Constants.MSG_DELETE_FAILED;
			}
		}
	}

	// @author A0105682H
	@Override
	public void undo() {
		if (matchingTasks.size() > Constants.ZERO) {
			logger.info(LogMessages.INFO_UNDO_ACTION);
			try {
				TaskItem task = toDoService.getLastDeletedTask();
				toDoService.createTask(task);
				feedback = Constants.MSG_REMOVE_UNDO;
				aimTasks.add(task);
			} catch (IOException e) {
				logger.error(LogMessages.ERROR_PARSE);
				feedback = Constants.MSG_UNDO_FAILED;
			}

		} else {
			feedback = Constants.MSG_UNDO_FAILED;
		}
	}
}
