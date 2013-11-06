package com.tobedone.command;

import java.util.Vector;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utilities.Constants;

public class ListCommand extends Command {

	Vector<TaskItem> allTasks;
	TaskItem.Status status;
	String scope ="";
	Vector<TaskItem> aimTasks;

	public ListCommand (String scope){
		aimTasks = new Vector<TaskItem>();
		allTasks = toDoService.getAllTasks();
		this.scope = scope;
	}
	
	protected void executeCommand() {
		// TODO Auto-generated method stub
		if (scope.equals("all")){
			aimTasks = allTasks;
			feedback = "al list";
			result = new CommandExecuteResult(aimTasks, feedback);
		} else if (scope.equals("unfinished")) {
			status = TaskItem.Status.UNFINISHED;
			for(int i=0; i<allTasks.size(); i++){
				TaskItem currentTask = allTasks.get(i);
				
				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				} 
			}
		} else if(scope.equals("finished")) {
			status = TaskItem.Status.FINISHED;
			for(int i=0; i<allTasks.size(); i++){
				TaskItem currentTask = allTasks.get(i);
				
				if (currentTask.getStatus().equals(status)) {
					aimTasks.add(currentTask);
				} 
			}
		}
		
		if (aimTasks.size() > 0){
			feedback = Constants.MSG_EMPTY_SEARCH;
			result = new CommandExecuteResult (aimTasks,feedback);
		} else {
			feedback = "Tasks are listed below: ";
			result = new CommandExecuteResult (aimTasks,feedback);
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
