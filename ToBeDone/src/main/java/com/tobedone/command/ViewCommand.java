package com.tobedone.command;

import java.io.IOException;
import java.util.Vector;

import com.tobedone.exception.TaskNotExistException;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;

public class ViewCommand extends Command {
	private int index = 0;
	private Vector<TaskItem> allTasks;
	
	public ViewCommand(int index){
		this.index = index;
		isUndoable = false;
		allTasks = toDoService.getAllTasks();
	}
	
	@Override
	protected void executeCommand() throws IOException, TaskNotExistException {
		// TODO Auto-generated method stub
		TaskItem task = allTasks.get(index);
		feedback = Constants.CMD_VIEW;
		aimTasks.add(task);
	}
}
