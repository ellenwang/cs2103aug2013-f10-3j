package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;


public class ClearCommand extends Command {

	private Vector<TaskItem> originalList;
	
	// @author A0105682H
	public ClearCommand() {
		super();
		originalList = new Vector<TaskItem>();
		isUndoable = true;
	}

	// @author A0105682H
	@Override
	public void executeCommand() {
		try {
			logger.info(LogMessages.INFO_CLEAR);
			originalList.clear();
			for (TaskItem task : toDoService.getAllTasks()) {
				originalList.add(task);
			}
			toDoService.clear();
			feedback = Constants.MSG_CLEAR;
			aimTasks = new Vector<TaskItem>();
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);
			feedback = Constants.MSG_CLEAR_FAILED;
		}
	}

	// @author A0105682H
	@Override
	public void undo() {
		try {
			for (TaskItem task : originalList) {
				toDoService.createTask(task);
				feedback = Constants.MSG_CLEAR_UNDO;
				aimTasks = originalList;
			}
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
			feedback = "";
		}
	}
}
