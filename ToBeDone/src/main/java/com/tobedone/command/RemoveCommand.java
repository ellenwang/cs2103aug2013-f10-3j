package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;


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
	private Vector<TaskItem> allTasks;
	// @author A0105682H
	public RemoveCommand(int index) {
		super();
		this.index = index;
		this.allTasks = toDoService.getAllTasks();
		isUndoable = true;
	}

	// @author A0105682H
	@Override
	protected void executeCommand() throws TaskNotExistException, IOException {
		logger.info(LogMessages.INFO_REMOVE);
		if (index<0 || index> allTasks.size()) {
			feedback = "";
		} else {
			toDoService.deleteTaskById(index);
		}
	}

	// @author A0105682H
	@Override
	public void undo() {
		if (allTasks.size() > Constants.ZERO) {
			logger.info(LogMessages.INFO_UNDO_ACTION);
				try {
					TaskItem task = toDoService.getLastDeletedTask();
					toDoService.createTask(task);
						feedback = Constants.MSG_REMOVE_UNDO;
					} 
				 catch (IOException e) {
					logger.error(LogMessages.ERROR_PARSE);
					feedback = Constants.MSG_UNDO_FAILED;
				}
			
		} else {
			feedback = Constants.MSG_UNDO_FAILED;
		}
	}
}
