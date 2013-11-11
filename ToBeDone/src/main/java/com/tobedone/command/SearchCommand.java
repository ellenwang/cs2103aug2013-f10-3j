//@author A0105682H
package com.tobedone.command;

import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class inherits from Command class and handles the execute and
 *        undo operation of search command.
 * 
 */
public class SearchCommand extends Command {
	private String keyword = null;

	// Constructor
	public SearchCommand(String keyword) {
		super();
		isUndoable = false;
		this.keyword = keyword.toLowerCase();
	}

	@Override
	/**
	 * Checks the keyword and calls the search method in logic and modifies feedback.
	 */
	protected void executeCommand() {
		if (keyword != (null)) {
			try {
				logger.info(LogMessages.INFO_SEARCH);

				Vector<TaskItem> taskList = toDoService.searchKeyword(keyword);
				feedback = String.format(Constants.MSG_SEARCH_SUCCESSFUL,
						keyword);
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
