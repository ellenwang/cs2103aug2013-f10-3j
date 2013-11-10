package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class FinishCommand extends Command {
	private TaskItem finishedTask;
	private Vector<TaskItem> matchingTasks;

	public FinishCommand(int index) {
		isUndoable = true;
		this.matchingTasks = toDoService.getMatchingTasks();
		finishedTask = matchingTasks.get(index);
	}

	// @author A0118441M
	@Override
	public void executeCommand() throws IOException, TaskNotExistException {
		aimTasks.clear();
		for (TaskItem task : matchingTasks) {
			aimTasks.add(task);
		}
		toDoService.deleteTask(finishedTask);
		finishedTask.setStatus(Status.FINISHED);
		toDoService.createTask(finishedTask);
	}

	// @author A0118441M
	@Override
	public void undo() {
		try {
			toDoService.deleteTask(finishedTask);
			finishedTask.setStatus(Status.UNFINISHED);
			toDoService.createTask(finishedTask);
			feedback = Constants.MSG_UNDO_SUCCESSFUL;
		} catch (TaskNotExistException e) {
			logger.error(LogMessages.ERROR_TASK_NOTFOUND);
		} catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
		}
	}
}
