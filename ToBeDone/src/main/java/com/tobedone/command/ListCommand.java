package com.tobedone.command;

import java.util.Vector;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;

public class ListCommand extends Command {

	Vector<TaskItem> allTasks;
	TaskItem.Status status;
	String scope = "";

	public ListCommand(String scope) {
		allTasks = toDoService.getAllTasks();
		this.scope = scope;
	}

	protected void executeCommand() {
		// TODO Auto-generated method stub
		if (scope.equals("all")) {
			for (TaskItem task : allTasks) {
				aimTasks.add(task);
			}
			feedback = "all list";
		} else if (scope.equals("unfinished")) {
			status = TaskItem.Status.UNFINISHED;
			for (int i = 0; i < allTasks.size(); i++) {
				TaskItem currentTask = allTasks.get(i);

				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				}
			}
		} else if (scope.equals("finished")) {
			status = TaskItem.Status.FINISHED;
			for (int i = 0; i < allTasks.size(); i++) {
				TaskItem currentTask = allTasks.get(i);

				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				}
			}
		}

		if (aimTasks.size() > 0) {
			feedback = Constants.MSG_EMPTY_SEARCH;
		} else {
			feedback = "Tasks are listed below: ";
		}
	}

	public String vectorToString(Vector<TaskItem> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + ". " + list.get(i) + '\n';
		}
		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
