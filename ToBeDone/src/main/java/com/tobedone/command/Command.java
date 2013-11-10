//@author A0105682H
package com.tobedone.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tobedone.command.utilities.CommandHistory;
import com.tobedone.exception.TaskNotExistException;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.taskitem.TaskItem;
import com.tobedone.utils.Constants;
import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.logic.ToDoList;

/**
 * @author A0105682H
 * @version 0.5
 * @since 01-10-2013
 * 
 *        This class is the super class of commands that handles the execute
 *        result from each command and push the undoable command into
 *        CommandHistory stack.
 * 
 */
public abstract class Command {
	protected static Logger logger = Logger.getLogger(Command.class);

	protected String feedback;
	protected Vector<TaskItem> aimTasks;
	protected CommandExecuteResult result;
	protected boolean isUndoable = false;
	protected boolean exitSystemStatus = false;
	protected static ToDoList toDoService = new ToDoListImp();

	// @author A0105682H
	public Command() {
		aimTasks = new Vector<TaskItem>();
		result = new CommandExecuteResult(aimTasks, feedback);
	}

	// @author A0105682H
	public boolean getExitSystemStatus() {
		return exitSystemStatus;
	}

	// @author A0105682H
	protected abstract void executeCommand() throws IOException,
			TaskNotExistException;

	// @author A0105682H
	public CommandExecuteResult execute() throws IOException,
			TaskNotExistException {
		this.executeCommand();
		this.addHistory();
		Collections.sort(result.getAimTasks(),
				Collections.reverseOrder(TaskItem.TaskItemComparator));
		toDoService.setMatchingTasks(result.getAimTasks());
		result.setAimTasks(aimTasks);
		result.setFeedback(feedback);
		System.out.println(result.getAimTasks().toString());
		System.out.println(result.getFeedback());
		return result;
	}

	// @author A0105682H
	public void undo() throws TaskNotExistException, IOException {
		this.feedback = Constants.EMPTY_STRING;
	}

	// @author A0105682H
	public String getFeedback() {
		return this.feedback;
	}

	// @author A0105682H
	public Vector<TaskItem> getAimTasks() {
		return this.aimTasks;
	}

	/**
	 * Put this command object into the CommandHistory stack.
	 * 
	 * @return true if command is undo-able
	 */
	// @author A0105682H
	public boolean addHistory() {
		CommandHistory list = CommandHistory.getInstance();
		if (this.isUndoable) {
			list.push(this);
			return true;
		}
		return false;
	}

//	public void assignAimTasks(String scope) {
//		aimTasks.clear();
//		switch (scope) {
//		case "all":	
//			for (TaskItem task : toDoService.getAllTasks()) {
//				aimTasks.add(task);
//			}
//			break;
//		case "match":
//			for (TaskItem task : toDoService.getMatchingTasks()) {
//				aimTasks.add(task);
//			}
//			break;
//		default: break;
//		}
//	}

	//@author A0105682H
	public String vectorToString(Vector<TaskItem> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + ". " + list.get(i) + Constants.NEWLINE;
		}
		if (!result.equals(Constants.EMPTY_STRING)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}



}