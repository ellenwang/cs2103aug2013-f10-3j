//@author A0105682H
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
 * @since 10-10-2013
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
	protected static ToDoList toDoService = new ToDoListImp();
	protected boolean executionSuccessful = false;
	protected boolean hasBeenUndone = false;
	protected boolean hasBeenRedone = false;
	protected boolean isUndoable = false;
	protected boolean exitSystemStatus = false;

	// Constructor
	public Command() {
		aimTasks = new Vector<TaskItem>();
		result = new CommandExecuteResult(aimTasks, feedback);
	}

	/**
	 * Executes the specific command input by the users.
	 * 
	 * @return The CommandExecuteResult instance with the feedback and aimTasks
	 *         from executing the command.
	 * @throws IOException
	 * @throws TaskNotExistException
	 *             The specified task it not found in the task list.
	 */
	public CommandExecuteResult execute() throws IOException,
			TaskNotExistException {
		this.executeCommand();
		if (executionSuccessful) {
			this.addHistory();
		}
		Collections.sort(result.getAimTasks(),
				Collections.reverseOrder(TaskItem.TaskItemComparator));
		toDoService.setMatchingTasks(result.getAimTasks());
		result.setAimTasks(aimTasks);
		result.setFeedback(feedback);
		return result;
	}

	/**
	 * Abstract method will be overridden by each specific command type and
	 * execute the command.
	 * 
	 * @throws IOException
	 * @throws TaskNotExistException
	 *             The specified task it not found in the task list.
	 */
	protected abstract void executeCommand() throws IOException,
			TaskNotExistException;

	/**
	 * General method to undo the command. Will be overridden.
	 * 
	 * @throws TaskNotExistException
	 *             The specified task it not found in the task list.
	 * @throws IOException
	 */
	public void undo() throws TaskNotExistException, IOException {
		this.feedback = Constants.EMPTY_STRING;
	}

	// Getters and Setters
	public boolean getExitSystemStatus() {
		return exitSystemStatus;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Vector<TaskItem> getAimTasks() {
		return aimTasks;
	}

	public void setAimTasks(Vector<TaskItem> aimTasks) {
		this.aimTasks = aimTasks;
	}
	
	/**
	 * Puts this command object into the CommandHistory stack.
	 * 
	 * @return true if command is successfully pushed into the stack.
	 */
	public boolean addHistory() {
		CommandHistory list = CommandHistory.getInstance();
		if (this.isUndoable) {
			list.push(this);
			return true;
		}
		return false;
	}

	/**
	 * Converts the information of a list of tasks into String.
	 * 
	 * @param list
	 *            The list of tasks to be converted.
	 * @return The String of all the information.
	 */
	public String vectorToString(Vector<TaskItem> list) {
		String result = Constants.EMPTY_STRING;
		for (int i = 0; i < list.size(); i++) {
			result += (i + 1) + Constants.DOT_FORMAT + list.get(i)
					+ Constants.NEWLINE;
		}
		if (!result.equals(Constants.EMPTY_STRING)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}