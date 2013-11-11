package com.tobedone.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Constants {
	// Error Message
	public static final String MSG_ERROR = "Error occured.";
	public static final String MSG_ERROR_NOT_FOUND = "No task found.";
	public static final String MSG_CLEAR_FAILED = "Clear operation failed!";
	public static final String MSG_CLEAR = "Tasks cleared.";
	public static final String MSG_CLEAR_UNDO = "Undo clear command.";

	// @author A0117215R
	// command name
	public static final String CMD_ADD = "add";
	public static final String CMD_REMOVE = "remove";
	public static final String CMD_UPDATE = "update";
	public static final String CMD_FINISH = "finish";
	public static final String CMD_HELP = "help";
	public static final String CMD_UNDO = "undo";
	public static final String CMD_REDO = "redo";
	public static final String CMD_VIEW = "view";
	public static final String CMD_LIST = "list";
	public static final String CMD_SEARCH = "search";
	public static final String CMD_UPLOAD = "upload";
	public static final String CMD_DOWNLOAD = "download";
	public static final String CMD_CLEAR = "clear";

	// @author A0117215R
	// command short name
	public static final String CMD_SHORTCUT_ADD = "+";
	public static final String CMD_SHORTCUT_REMOVE = "-";
	public static final String CMD_SHORTCUT_UPDATE = "++";
	public static final String CMD_SHORTCUT_FINISH = "f";
	public static final String CMD_SHORTCUT_HELP = "?";
	public static final String CMD_SHORTCUT_UNDO = "u";
	public static final String CMD_SHORTCUT_VIEW = "v";
	public static final String CMD_SHORTCUT_LIST_FINISHED = "lsf";
	public static final String CMD_SHORTCUT_LIST_UNFINISHED = "lsu";
	public static final String CMD_SHORTCUT_LIST_ALL = "lsa";
	public static final String CMD_SHORTCUT_SEARCH = "s";
	public static final String CMD_SHORTCUT_CLEAR = "cl";
	public static final String CMD_SHORTCUT_UPLOAD = "gu";
	public static final String CMD_SHORTCUT_DOWNLOAD = "gd";

	// @author A0117215R
	// commandparser class name
	public static final String PARSER_CLA_NAME_ADD = "AddCommandParser";
	public static final String PARSER_CLA_NAME_REMOVE = "RemoveCommandParser";
	public static final String PARSER_CLA_NAME_UPDATE = "UpdateCommandParser";
	public static final String PARSER_CLA_NAME_VIEW = "ViewCommandParser";
	public static final String PARSER_CLA_NAME_SEARCH = "SearchCommandParser";
	public static final String PARSER_CLA_NAME_UNDO = "UndoCommandParser";
	public static final String PARSER_CLA_NAME_REDO = "RedoCommandParser";
	public static final String PARSER_CLA_NAME_LIST = "ListCommandParser";
	public static final String PARSER_CLA_NAME_CLEAR = "ClearCommandParser";
	public static final String PARSER_CLA_NAME_UPLOAD = "SyncUpCommandParser";
	public static final String PARSER_CLA_NAME_DOWNLOAD = "SyncDownCommandParser";
	public static final String PARSER_CLA_NAME_HELP = "HelpCommandParser";
	public static final String PARSER_CLA_NAME_FINISH = "FinishCommandParser";
	public static final String PARSER_CLA_NAME_LSA = "ListAllCommandParser";
	public static final String PARSER_CLA_NAME_LSF = "ListFinishedCommandParser";
	public static final String PARSER_CLA_NAME_LSU = "ListUnfinishedCommandParser";

	// @author A0117215R
	// priority
	public static final String STR_PRI_HIGH = "high";
	public static final String STR_PRI_NORMAL = "medium";
	public static final String STR_PRI_LOW = "low";
	public static final String STR_SHORT_PRI_HIGH = "h";
	public static final String STR_SHORT_PRI_NORMAL = "m";
	public static final String STR_SHORT_PRI_LOW = "l";
	public static final int INT_PRI_HIGH = 3;
	public static final int INT_PRI_NORMAL = 2;
	public static final int INT_PRI_LOW = 1;
	public static final int INT_PRI_WRONG = -1;
	public static final int INT_PRI_NO_CHANGE = 0;

	// @author A0117215R
	// Tips for each command.
	public static final String TIP_ADD = "add/+ [description] [...]";
	public static final String TIP_UPDATE = "update/++ [index] [...]";
	public static final String TIP_REMOVE = "remove/- [index]";
	public static final String TIP_SEARCH = "search/s [...]";
	public static final String TIP_LIST = "list [...] OR lsa/lsu/lsf";
	public static final String TIP_UPLOAD = "upload/upl";
	public static final String TIP_DOWNLOAD = "download/d";
	public static final String TIP_UNDO = "undo/u";
	public static final String TIP_HELP = "help/?";
	public static final String TIP_CLEAR = "clear";
	public static final String TIP_WRONG = "No such command";
	public static final String TIP_REDO = "redo";
	public static final String TIP_SEPRETER = " OR ";
	public static final String TIP_BEGIN = "Input command above";
	public static final int INPUT_EMPTY = -1;

	// @author A0117215R
	// Priority
	public static final int PRIORITY_HIGH = 3;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 1;
	public static final int DEFAULT_PRIORITY = 2;

	// @author A0117215R
	// task list info in GUI
	public static final String INFO_UNFINISHED_TASKS = "Below are unfinished tasks, finish them as soon as possible :)";
	public static final String INFO_SEARCH = "Below are search result";
	public static final String INFO_ALL_TASKS = "Below are all of your tasks";
	public static final String INFO_FINISHED_TASKS = "Below are your finished tasks";
	public static final String INFO_ADD = "Below task has been added";
	public static final String INFO_REMOVE = "Below task has been removed";
	public static final String INFO_SYNC = "Below are tasks added from your google calander";

	// @author A0117215R
	// Exception msg for parser
	public static final String MSG_ERROR_WRONG_DATE = "Wrong date format :(";
	public static final String MSG_ERROR_EMPTY_DESCRIPTION = "No description found :(";
	public static final String MSG_ERROR_EMPTY_ARGUMENT_INDEX = "No index found :(";
	public static final String MSG_ERROR_EMPTY_ARGUMENT = "No argument found";
	public static final String MSG_ERROR_INVALID = "No such command :(";
	public static final String MSG_ERROR_INVALID_ARGUMENT = "Invalid argument :(";
	public static final String MSG_ERROR_INVALID_ARGUMENT_INDEX = "Please check task index :(";

	// @author A0117215R
	// parameter for List command
	public static final String PARAMETER_LIST_ALL = "all";
	public static final String PARAMETER_LIST_FINISHED = "finished";
	public static final String PARAMETER_LIST_UNFINISHED = "unfinished";

	// @author A0117215R
	// below are regular expression for date parsing
	// HH:mm
	public static final String REGEX_DATE_TIME = "((((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:(?:am|AM|pm|PM))?))|(((?:[1][0-2])|(?:[0-9]))((PM)|(pm)|(AM)|(am))))"; // HourMinuteSec
	public static final String REGEX_DATE_SPACE = "(\\s*)"; // White Space
	// dd-mm-yyyy
	public static final String REGEX_DATE_DATE = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // DDMMYYYY

	public static final String DAY_OF_A_WEEK = "(monday|mon|tuesday|tues|wednesday|wed|thursday|thur"
			+ "|friday|fri|saturday|sat|sunday|sun)";
	public static final String THIS_NEXT = "(this|next)";
	public static final String DAY_OF_TWO_WEEK = THIS_NEXT + REGEX_DATE_SPACE
			+ DAY_OF_A_WEEK;

	// short_of_day
	public static final String REGEX_SHORT_DAY = "(today|tomorrow)" + "|" + "("
			+ DAY_OF_TWO_WEEK + ")" + "|" + DAY_OF_A_WEEK;
	// dd-mm
	public static final String REGEX_DATE_DAY = "("
			+ "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012])(?![\\d]))"
			+ "|" + "(" + REGEX_SHORT_DAY + ")" + ")";

	// HH:mm dd-mm
	public static final String DATE_TDM = REGEX_DATE_TIME + REGEX_DATE_SPACE
			+ REGEX_DATE_DAY;
	// HH:mm dd-mm-yyyy
	public static final String DATE_TDMY = REGEX_DATE_TIME + REGEX_DATE_SPACE
			+ REGEX_DATE_DATE;

	public static final String REGEX_DATE_PREFIX = "(from|to|by)";
	public static final String REGEX_FROM_PREFIX = "from";
	public static final String REGEX_TO_PREFIX = "to";
	public static final String REGEX_BY_PREFIX = "by";

	public static final String REGEX_ONE_DATE = DATE_TDMY + "|" + DATE_TDM
			+ "|" + REGEX_DATE_TIME + "|" + REGEX_DATE_DATE + "|"
			+ REGEX_DATE_DAY;

	public static final String DATE = REGEX_DATE_PREFIX + REGEX_DATE_SPACE
			+ "(" + REGEX_ONE_DATE + ")";
	public static final String FROM_DATE = REGEX_FROM_PREFIX + REGEX_DATE_SPACE
			+ "(" + REGEX_ONE_DATE + ")" + REGEX_DATE_SPACE;
	public static final String TO_DATE = REGEX_TO_PREFIX + REGEX_DATE_SPACE
			+ "(" + REGEX_ONE_DATE + ")" + REGEX_DATE_SPACE;
	public static final String BY_DATE = REGEX_BY_PREFIX + REGEX_DATE_SPACE
			+ "(" + REGEX_ONE_DATE + ")" + REGEX_DATE_SPACE;
	public static final String FROM_TO_DATE = "(" + FROM_DATE + ")"
			+ REGEX_DATE_SPACE + "(" + TO_DATE + ")" + REGEX_DATE_SPACE;

	public static final Pattern FROM_PATTERN = Pattern.compile(FROM_DATE,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern TO_PATTERN = Pattern.compile(TO_DATE,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern BY_PATTERN = Pattern.compile(BY_DATE,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern FROM_TO_PATTERN = Pattern.compile(FROM_TO_DATE,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern ONE_DATE_PATTERN = Pattern.compile(
			REGEX_ONE_DATE, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern TIME_PATTERN = Pattern.compile(REGEX_DATE_TIME,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern DAY_PATTERN = Pattern.compile(REGEX_DATE_DAY,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern DATE_PATTERN = Pattern.compile(REGEX_DATE_DATE,
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	public static final Pattern SHORT_DAY_PATTERN = Pattern.compile(
			DAY_OF_A_WEEK, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// @author A0105682H
	// Feedback
	public static final String MSG_ADD_FAILED = "Add operation failed!";
	public static final String MSG_ADD_SUCCESSFUL = "Added task: %1$s";
	public static final String MSG_ALLTASK_LIST = "All tasks are listed as below: ";
	public static final String MSG_CHECK_SEARCH_SYNTAX = "Please check search syntax.";
	public static final String MSG_DELETE_FAILED = "Failed to remove the task.";
	public static final String MSG_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	public static final String MSG_EXIT = "Thank you for using ToBeDone.";
	public static final String MSG_FINISH_SUCCESSFUL = "Finished task: %1$s";
	public static final String MSG_INDEX_OUT_OF_BOUNDS = "There is no task with that index.";
	public static final String MSG_INVALID_DEADLINE = "Invalid time. The deadline of the new task has already passed.";
	public static final String MSG_INVALID_TIMEPERIOD = "Invalid time period. The start time of the timed task is after its end task.";
	public static final String MSG_INVALID_INDEX = "The index is invalid.";
	public static final String MSG_MATCHINGTASK_LIST = "Matching tasks are listed as below: ";
	public static final String MSG_NO_MATCHING_TASK = "No matching task to show.";
	public static final String MSG_NO_UNDO = "Unable to undo command.";
	public static final String MSG_REDO_TWICE_FAILED = "Can not redo more than one time.";
	public static final String MSG_REMOVE_UNDO = "Restored task: %1$s";
	public static final String MSG_SEARCH_SUCCESSFUL = "Searching for tasks which contains: %1%s";
	public static final String MSG_TASK_ALREADY_EXISTS = "Task already exists.";
	public static final String MSG_UNDO_ADD_FAILED = "Failed to undo the add command.";
	public static final String MSG_UNDO_CLEAR_FAILED = "Failed to undo the clear command.";
	public static final String MSG_UNDO_FAILED = "Undo failed! ";
	public static final String MSG_UNDO_SUCCESSFUL = "Successfully undid the command.";
	public static final String MSG_UNDO_TWICE_FAILED = "Can not undo more than one time.";
	public static final String MSG_UNDO_UPDATE_FAILED = "Failed to undo update.";
	public static final String MSG_UPDATE_SUCCESSFUL = "Task has been successfully updated.";

	// Resource Path
	public static final String HELP_PATH = "/help";

	// others
	public static final String DEADLINE_TASK_FORMAT = "\n\tdeadline: ";
	public static final String DOT_FORMAT = ". ";
	public static final String EMPTY_STRING = "";
	public static final String TIMED_TASK_FORMAT_FROM = "\n\tfrom: ";
	public static final String TIMED_TASK_FORMAT_TO = "\n\tto:   ";
	public static final int SERVICE_OPERATION_FAILED_ID = -1;
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final String NEWLINE = "\n";
	public static final String currentYear = (Calendar.getInstance())
			.get(Calendar.YEAR) + Constants.EMPTY_STRING;
	public static final String currentMonth = (Calendar.getInstance())
			.get(Calendar.MONTH) + Constants.EMPTY_STRING;
	public static final String currentDay = (Calendar.getInstance())
			.get(Calendar.DAY_OF_MONTH) + Constants.EMPTY_STRING;

	// packages name
	public static final String PACK_PARSER = "com.tobedone.parser.";
	public static final String PACK_EXCEPTION = "com.tobedone.exception.";

	// Error Message
	public static final String MSG_ERROR_INPUT_A_FUTURE_DATE_AND_TIME = "Please input a future date and time.";
	public static final String MSG_ERROR_MISSING_QUOTES = "Please check content syntax.";
	public static final String MSG_ERROR_NO_TASK_CONTENT = "There is no task content.";
	public static final String MSG_ERROR_NO_ARG_EXPECTED = "Please check syntax: no argument is expected.";

	// Others

	public static final String TOBEDONE = "2BeDone";
	public static final String EXIT_TOBEDONE = "exit";
	public static final String TASK = "Task ";
	public static final String TIME_ZONE = "UTC";
	public static final String TIME_ZONE_GMT = "Etc/GMT";
	public static final String NOTFOUND = " not found.";

	public static final String POP_OPERATION = "pop";
	public static final String POP_UNDONE_OPERATION = "popUndone";
	public static final String PUSH_OPERATION = "push";

	public static final String ALL_SCOPE = "all";
	public static final String FINISHED_SCOPE = "finished";
	public static final String UNFINISHED_SCOPE = "unfinished";
	public static final String MATCH_SCOPE = "match";
	public static final int COMMAND_WINDOW_SIZE = 5;
	public static final int TIMER_PERIOD = 2 * 1000;
	public static final int TABLE_ROW_MAX_COUNT = 100;
	public static final int TIME_INTERVAL = 15;
	public static final int NUM_OF_DAYS_TO_POSTPONE = 4;

	public static final int DAYS_OF_A_WEEK = 7;
	public static final int TWO = 2;
	public static final int ONE_LOOKAHEAD = 1;
	public static final int HOURS_OF_A_DAY = 12;
	public static final int NOT_FOUND_INDEX = -1;

	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String HYPHEN = "-";
	public static final String COLON = ":";

	public static final String CONTENT_WITH_QUOTE = ": \"%s\" ";
	public static final String DEFAULT_START_TIME = "00:01";
	public static final String DEFAULT_END_TIME = "23:59";
	public static final String CLOCK = "00";
	public static final String TIME_AM = "am";
	public static final String TIME_PM = "pm";
	public static final String SPLIT_SPACE = "\\s+";
	public static final String CURLY_BRACES = "{}";
	public static final String USER = "user";
	public static final String TAG_SYMBOL = "@";
	public static final String CONTENT_SYMBOL = "\"";
	public static final String DATE_SYMBOL = ">";
	public static final String UNUSABLE_SERVICE_STATE = "Unusable Service Implementation";
	public static final String ERROR_CODE_FOR_TEST = "asdfgh";
	public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"HH:mm,dd-MM-yyyy");

	public static final String MESSAGE_TASK_ILLEGAL_INSTANCE_OF = "Task not instance of any of the instantiable subclasses of TaskItem";

	// GoogleSync
	public static final String MESSAGE_DOWNLOAD_SUCCESS = "The following tasks have been added to local calendar.";
	public static final String MESSAGE_NO_UPDATE_TO_LOCAL = "No new task found.";
	public static final String MESSAGE_UPLOAD_SUCCESS = "The following tasks have been uploaded to Google Calendar.";
	public static final String SERVER_UNAVAILABLE = "Cannot connect to Google server now.";
	public static final String MSG_GCAL_NOT_AUTHORIZED = "Authentication erroe.";
	public static final int NEWLY_ADD_CALENDAR_ID = 1;
	public static final String DEFAULT_CALENDAR = "primary";
	// Storage
	public static final String MESSAGE_FAILED_TO_WRITE_TO_FILE = "Failed to write to file.";
	public static final String MESSAGE_FAILED_TO_READ_FROM_FILE = "Failed to read from file.";
	public static final String MESSAGE_FAILED_TO_PARSE_DATE = "Failed to parse date.";
	public static final String MESSAGE_STORE_SUCCESSFUL = "Successfully stored tasks.";
	public static final String MESSAGE_TASK_LIST_NULL = "Store aborted, list of tasks is null";
	public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
	public static final String FILE_NAME = "ToBeDone.txt";
	public static final String TEST_FILE_NAME = "test.txt";
	public static final String MSG_ALREADY_FINISHED = "Task has already been finished";

}
