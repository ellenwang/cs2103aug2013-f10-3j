package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;
import com.tobedone.utilities.LogMessages;

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
		toDoService.setLastUpdatedTask(toDoService.getAllTasks().get(index));
	}
	
	public void undo(){
		try {
			TaskItem task = toDoService.getLastUpdatedTask();
			TaskItem.Status status = TaskItem.Status.UNFINISHED;
			task.setStatus(status);
			toDoService.deleteTask(task);
			toDoService.createTask(task);
		} catch (TaskNotExistException e) {
			logger.error(LogMessages.ERROR_TASK_NOTFOUND);
		}  catch (IOException e) {
			logger.error(LogMessages.ERROR_FILE);
		}
	}
}
