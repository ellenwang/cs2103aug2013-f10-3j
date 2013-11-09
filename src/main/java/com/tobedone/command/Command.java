package com.tobedone.command;

import java.io.IOException;
import java.text.ParseException;
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
import com.tobedone.logic.ToDoListImp;

public abstract class Command {
	protected static Logger logger = Logger.getLogger(Command.class);
	protected String feedback;
	protected CommandExecuteResult result;
	protected boolean isUndoable = false;
	protected boolean exitSystemStatus = false;
	protected static ToDoList toDoService = new ToDoListImp();
	protected Vector<TaskItem> aimTasks;

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

	public Vector<TaskItem> getAimTasks() {
		return this.aimTasks;
	}

	/**
	 * Put this command object into the window queue
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