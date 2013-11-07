package com.tobedone.command;

import java.io.IOException;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class FinishCommand extends Command {
	private int index;
	private TaskItem finishedTask;

	public FinishCommand(int index) {
		isUndoable = true;
		this.index = index;
		finishedTask = null;
	}

	// @author A0105682H
	@Override
	public void executeCommand() throws IOException, TaskNotExistException {
		toDoService.completeTask(index);
		finishedTask = toDoService.getMatchingTasks().get(index);
		aimTasks.add(finishedTask);
	}

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
