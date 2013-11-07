package com.tobedone.command;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class SearchCommand extends Command {
	private String keyword = null;

	public SearchCommand(String keyword) {
		super();
		isUndoable = false;
		this.keyword = keyword;
	}

	@Override
	protected void executeCommand() {
		// TODO Auto-generated method stub
		if (keyword != (null)) {
			try {
				logger.info(LogMessages.INFO_SEARCH);
				Vector<TaskItem> taskList = toDoService.searchKeyword(keyword);
				feedback = Constants.MSG_ADDED_FAILED;
				for (TaskItem task : taskList) {
					aimTasks.add(task);
				}
			} catch (TaskNotExistException e) {
				logger.error(LogMessages.ERROR_TASK_NOTFOUND);
				feedback = Constants.MSG_ERROR_NOT_FOUND;
			}
		} else {
			logger.debug(LogMessages.DEBUG_SYNTAX);
			feedback = Constants.MSG_CHECK_SEARCH_SYNTAX;
		}
	}

}
