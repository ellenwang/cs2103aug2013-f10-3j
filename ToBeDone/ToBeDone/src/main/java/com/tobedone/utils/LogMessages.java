//@author A0105682H
package com.tobedone.utils;

public class LogMessages {
	// ERROR
	public static final String ERROR_ADD_UNDO_FAILED = "Failed to undo create.";
	public static final String ERROR_FILE = "File IOException.";
	public static final String ERROR_PARSE = "Parsing exception.";
	public static final String ERROR_TASK_NOTFOUND = "Task not found.";
	public static final String ERROR_UPDATE_UNDO_FAILED = "Failed to undo update.";

	// INFO FROM LOGIC
	public static final String INFO_ADD = "Add a new task.";
	public static final String INFO_ADD_UNDO = "Undo the create command.";
	public static final String INFO_CLEAR = "Clear tasks.";
	public static final String INFO_COMPLETE_TASK = "Complete a task.";
	public static final String INFO_CREATE_TASK = "Create a new task.";
	public static final String INFO_DELETE = "Delete a task by index.";
	public static final String INFO_DELETE_TASK = "Delete a task.";
	public static final String INFO_DELETE_TASK_BY_ID = "Delete a task by ID.";
	public static final String INFO_DISPLAY_DEFAULT_ORDER = "Display task list sorted by priority, deadline/endtime, start time, alphebatically.";
	public static final Object INFO_EXIT = "Exit.";
	public static final String INFO_FINISH = "Finish a task.";
	public static final String INFO_HELP = "Help.";
	public static final String INFO_POP_COMMAND = "Pop command from command stack.";
	public static final String INFO_POP_UNDO_COMMAND = "Pop undone command from undo command stack.";
	public static final String INFO_PUSH_COMMAND = "Push command into command stack.";
	public static final String INFO_REMOVE = "Remove a task.";
	public static final String INFO_SEARCH = "Executing search command.";
	public static final String INFO_SEARCH_KEYWORD = "Search by keyword.";
	public static final String INFO_UNDO_ACTION = "Undo previous action";
	public static final String INFO_UPDATE_TASK = "Update a task.";

	// DEBUG
	public static final String DEBUG_NO_CONTENT = "Empty task content.";
	public static final String DEBUG_UNDO_NOTHING = "No available actions to undo";
	public static final String DEBUG_SYNTAX = "Checking arguments of the command.";
	public static final String DEBUG_NOT_REMOVED = "No task to be removed.";

}
