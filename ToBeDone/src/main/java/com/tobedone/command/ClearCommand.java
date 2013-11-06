package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;


public class ClearCommand extends Command {

	private Vector<TaskItem> originalList;
	private Vector<TaskItem> aimTasks = new Vector<TaskItem>();
	
	// @author A0105682H
	public ClearCommand() {
		super();
		isUndoable = true;
	}

	// @author A0105682H
	@Override
	public void executeCommand() {
		try {
			logger.info(LogMessages.INFO_CLEAR);
			originalList = toDoService.getAllTasks();
			toDoService.clear();
			feedback = Constants.MSG_CLEAR;
			result = new CommandExecuteResult(aimTasks, feedback);
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_PARSE);
			feedback = Constants.MSG_CLEAR_FAILED;
			result = new CommandExecuteResult(aimTasks, feedback);
		}
	}

	// @author A0105682H
	@Override
	public void undo() {
		try {
			for (TaskItem task : originalList) {
				toDoService.createTask(task);
				feedback = Constants.MSG_CLEAR_UNDO;
			}
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
			feedback = "";
		}
	}
}
