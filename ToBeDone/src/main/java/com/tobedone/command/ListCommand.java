package com.tobedone.command;

import java.util.Vector;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

public class ListCommand extends Command {

	Vector<TaskItem> allTasks;
	TaskItem.Status status;
	String scope = Constants.EMPTY_STRING;

	public ListCommand(String scope) {
		allTasks = toDoService.getAllTasks();
		this.scope = scope;
	}

	protected void executeCommand() {
		if (scope.equals(Constants.ALL_SCOPE)) {
			for (TaskItem task : allTasks) {
				aimTasks.add(task);
			}
			
		} else if (scope.equals(Constants.UNFINISHED_SCOPE)) {
			status = TaskItem.Status.UNFINISHED;
			for (int i = 0; i < allTasks.size(); i++) {
				TaskItem currentTask = allTasks.get(i);

				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				}
			}
		} else if (scope.equals(Constants.FINISHED_SCOPE)) {
			status = TaskItem.Status.FINISHED;
			for (int i = 0; i < allTasks.size(); i++) {
				TaskItem currentTask = allTasks.get(i);

				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				}
			}
		}

		if (aimTasks.size() > 0) {
			if(aimTasks.size() == allTasks.size()){
				feedback = Constants.MSG_ALLTASK_LIST;
			} else {
				feedback = Constants.MSG_MATCHINGTASK_LIST;
			}
		} else {
			feedback = Constants.MSG_NO_MATCHING_TASK;
		}
	}

	public String vectorToString(Vector<TaskItem> list) {
		String result = Constants.EMPTY_STRING;
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + ". " + list.get(i) + Constants.NEWLINE;
		}
		if (!result.equals(Constants.EMPTY_STRING)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
