package com.tobedone.command;

import java.util.Vector;

import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

/**
 * @author A0105682H
 * @version 0.5
 * @since 10-10-2013
 * 
 *        This class extends Command class and handles the execute and undo
 *        operation of list command.
 * 
 */
public class ListCommand extends Command {

	Vector<TaskItem> allTasks;
	TaskItem.Status status;
	String scope = Constants.EMPTY_STRING;

	// Constructor
	public ListCommand(String scope) {
		allTasks = toDoService.getAllTasks();
		this.scope = scope;
	}

	/**
	 * Filters all matching tasks tracked by their status.
	 */
	public void executeCommand() {
		if (scope.equals(Constants.ALL_SCOPE)) {
			for (TaskItem task : allTasks) {
				aimTasks.add(task);
			}
		} else if (scope.equals(Constants.UNFINISHED_SCOPE)) {
			status = TaskItem.Status.UNFINISHED;
			this.assignAimTasks(status);
		} else if (scope.equals(Constants.FINISHED_SCOPE)) {
			status = TaskItem.Status.FINISHED;
			this.assignAimTasks(status);
		}

		if (aimTasks.size() > 0) {
			if (aimTasks.size() == allTasks.size()) {
				feedback = Constants.MSG_ALLTASK_LIST;
			} else {
				feedback = Constants.MSG_MATCHINGTASK_LIST;
			}
		} else {
			feedback = Constants.MSG_NO_MATCHING_TASK;
		}
	}
	
	public void assignAimTasks (TaskItem.Status status){
		for (int i = 0; i < allTasks.size(); i++) {
			TaskItem currentTask = allTasks.get(i);
			if (currentTask.getStatus().equals(status)) {
				aimTasks.add(currentTask);
			}
		}
	}
}
