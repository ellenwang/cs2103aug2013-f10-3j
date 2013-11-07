package com.tobedone.command;

import java.io.IOException;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

public class FinishCommand extends Command{
	private int index;

	public FinishCommand(int index) {
		isUndoable = true;
		this.index = index;
	}

	// @author A0105682H
	@Override
	public void executeCommand() throws IOException, TaskNotExistException {
		toDoService.completeTask(index);
		TaskItem currentTask = toDoService.getAllTasks().get(index);
		toDoService.setLastUpdatedTask(currentTask);
		aimTasks.add(currentTask);
	}
	
	public void undo(){
		try {
			TaskItem task = toDoService.getLastUpdatedTask();
			TaskItem.Status status = TaskItem.Status.UNFINISHED;
			task.setStatus(status);
			toDoService.deleteTask(task);
			toDoService.createTask(task);
			feedback = Constants.MSG_UNDO_SUCCESSFUL;
			aimTasks.add(task);
		} catch (TaskNotExistException e) {
			logger.error(LogMessages.ERROR_TASK_NOTFOUND);
		}  catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
		}
	}
}
