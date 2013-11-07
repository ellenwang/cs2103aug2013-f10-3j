package com.tobedone.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//@author A0105682H
public class Constants {
	// Error Message
	public static final String MSG_ERROR = "Error occured.";
	public static final String MSG_ERROR_NOT_FOUND = "No task found.";
	public static final String MSG_CLEAR_FAILED = "Clear operation failed!";
	public static final String MSG_CLEAR = "Tasks cleared.";
	public static final String MSG_CLEAR_UNDO = "Undo clear command.";

	public static final String TIP_ADD = "add/+ [description] [...]";
	public static final String TIP_UPDATE = "update/++ [index] [...]";
	public static final String TIP_REMOVE = "remove/- [index]";
	public static final String TIP_SEARCH = "search/s [...]";
	public static final String TIP_LIST = "lsa/lsu/luf";
	public static final String TIP_SYNC = "sync/g";
	public static final String TIP_HELP = "help/?";
	public static final String TIP_CLEAR = "clear/cl";
	public static final String TIP_WRONG = "No such command";
	public static final String TIP_SEPRETER = " OR ";
	public static final String TIP_BEGIN = "Input command above";
	public static final int INPUT_EMPTY = -1;

	// Priority
	public static final int PRIORITY_HIGH = 3;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 1;
	public static final int DEFAULT_PRIORITY = 2;
	// Feedback
	public static final String MSG_NO_UNDO = "Unable to undo actions.";
	public static final String MSG_CHECK_SEARCH_SYNTAX = "Please check search syntax.";
	public static final String MSG_EXIT = "Thank you for using ToBeDone.";
	public static final String MSG_FINISH_SUCCESSFUL = "Finished task: \"%1$s\".";
	public static final String MSG_EMPTY_SEARCH = "No matching task to show.";
	public static final String MSG_CREATE_SUCCESSFUL = "Created task: %1$s";
	public static final String MSG_DELETE_FAILED = "Failed to remove the task.";
	public static final String MSG_UNDO_SUCCESSFUL = "Successfully undo the action.";

	public static final String MSG_ADDED_FAILED = "Add operation failed!";
	public static final String MSG_UNDO_FAILED = "Undo failed! ";
	public static final String MSG_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	public static final String MSG_REMOVE_UNDO = "Undo remove.";
	public static final String MSG_UNDO_UPDATE_FAILED = "Failed to undo update.";
	public static final String MSG_UPDATE_SUCCESSFUL = "Old task:\t%1$s\nUpdated task:\t%2$s";

	// task list info for GUI
	public static final String INFO_UNFINISHED_TASKS = "Below are expiring tasks, finish them as soon as possible :)";
	public static final String INFO_SEARCH = "Below are search result";
	public static final String INFO_ALL_TASKS = "Below are all of your tasks";
	public static final String INFO_FINISHED_TASKS = "Below are your finished tasks";
	public static final String INFO_ADD = "Below task has been added";
	public static final String INFO_REMOVE = "Below task has been removed";
	public static final String INFO_UPDATE = "Task 1 has been changed into Task 2";
	public static final String INFO_SYNC = "Below are tasks added from your google calander";

	// List tasks switch options
	public static final int LIST_TASK_DATE_SORTED = 1;
	// Resource Path
	public static final String HELP_PATH = "/help";

	// others
	public static final String EMPTY_STRING = "";
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

	// Commands
	// Tian Xing A0117215R
	public static final String CMD_ADD = "add";
	public static final String CMD_REMOVE = "remove";
	public static final String CMD_UPDATE = "update";
	public static final String CMD_FINISH = "finish";
	public static final String CMD_HELP = "help";
	public static final String CMD_UNDO = "undo";
	public static final String CMD_VIEW = "view";
	public static final String CMD_LIST = "list";
	public static final String CMD_SEARCH = "search";
	public static final String CMD_SYNC = "sync";
	public static final String CMD_CLEAR = "delete all";

	// SHORTCUT COMMANDS
	// Tian Xing A0117215R
	public static final String CMD_SHORTCUT_ADD = "+";
	public static final String CMD_SHORTCUT_REMOVE = "-";
	public static final String CMD_SHORTCUT_UPDATE = "++";
	public static final String CMD_SHORTCUT_FINISH = "f";
	public static final String CMD_SHORTCUT_HELP = "?";
	public static final String CMD_SHORTCUT_UNDO = "u";
	public static final String CMD_SHORTCUT_VIEW = "v";
	public static final String CMD_SHORTCUT_LIST_FINISHED = "lsf";
	public static final String CMD_SHORTCUT_LIST_UNFINISHED = "lsu";
	public static final String CMD_SHORTCUT_LIST_ALL = "ls";
	public static final String CMD_SHORTCUT_SEARCH = "s";
	public static final String CMD_SHORTCUT_CLEAR = "cl";
	public static final String CMD_SHORTCUT_SYNC = "g";

	// commandClass name
	// Tian Xing A0117215R
	public static final String PARSER_CLA_NAME_ADD = "AddCommandParser";
	public static final String PARSER_CLA_NAME_REMOVE = "RemoveCommandParser";
	public static final String PARSER_CLA_NAME_UPDATE = "UpdateCommandParser";
	public static final String PARSER_CLA_NAME_VIEW = "ViewCommandParser";
	public static final String PARSER_CLA_NAME_SEARCH = "SearchCommandParser";
	public static final String PARSER_CLA_NAME_UNDO = "UndoCommandParser";
	public static final String PARSER_CLA_NAME_LIST = "ListCommandParser";
	public static final String PARSER_CLA_NAME_CLEAR = "ClearCommandParser";
	public static final String PARSER_CLA_NAME_SYNC = "SyncCommandParser";
	public static final String PARSER_CLA_NAME_HELP = "HelpCommandParser";
	public static final String PARSER_CLA_NAME_FINISH = "FinishCommandParser";
	public static final String PARSER_CLA_NAME_LSA = "ListAllCommandParser";
	public static final String PARSER_CLA_NAME_LSF = "ListFinishedCommandParser";
	public static final String PARSER_CLA_NAME_LSU = "ListUnfinishedCommandParser";

	// Priority
	// Tian Xing A0117215R
	public static final String STR_PRI_HIGH = "high";
	public static final String STR_PRI_NORMAL = "normal";
	public static final String STR_PRI_LOW = "low";
	public static final String STR_SHORT_PRI_HIGH = "h";
	public static final String STR_SHORT_PRI_NORMAL = "n";
	public static final String STR_SHORT_PRI_LOW = "l";
	public static final int INT_PRI_HIGH = 3;
	public static final int INT_PRI_NORMAL = 2;
	public static final int INT_PRI_LOW = 1;
	public static final int INT_PRI_WRONG = -1;
	public static final int INT_PRI_NO_CHANGE = 0;

	public static final String REGEX_DATE_SHORT = "(today)|(tomorrow)|(mon)|(tues)|(wed)|(thur)|(fri)|(sat)|(sun)";
	public static final String REGEX_DATE_TODAY = "today";
	public static final String REGEX_DATE_TOMORROW = "tomorrow";
	public static final String REGEX_DATE_FROM_PREFIX = "from"; // from prefix
	public static final String REGEX_DATE_BY_PREFIX = "by"; // by prefix
	public static final String REGEX_DATE_TO_PREFIX = "to"; // to prefix
	// HH:mm
	public static final String REGEX_DATE_TIME = "(((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:(?:am|AM|pm|PM))?))|(((?:[1][0-2])|(?:[0-9]))((PM)|(pm)|(AM)|(am)))"; // HourMinuteSec
	public static final String REGEX_DATE_SPACE = "(\\s+)"; // White Space
	// dd-mm-yyyy
	public static final String REGEX_DATE_DATE = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // DDMMYYYY
	// dd-mm
	public static final String REGEX_DATE_DAY = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012]))(?![\\d])";

	// packages name
	public static final String PACK_PARSER = "com.tobedone.parser.";
	public static final String PACK_EXCEPTION = "com.tobedone.exception.";

	// Error Message

	public static final String MSG_ERROR_INVALID = "Invalid command.";
	public static final String MSG_ERROR_INVALID_ARGUMENT = "Invalid argument.";
	public static final String MSG_ERROR_INVALID_ARGUMENT_INDEX = "Invalid index.";
	public static final String MSG_ERROR_EMPTY_ARGUMENT_INDEX = "Index not found.";
	public static final String MSG_ERROR_INPUT_A_FUTURE_DATE_AND_TIME = "Please input a future date and time.";
	public static final String MSG_ERROR_MISSING_QUOTES = "Please check content syntax.";
	public static final String MSG_ERROR_NO_TASK_CONTENT = "There is no task content.";
	public static final String MSG_ERROR_NO_ARG_EXPECTED = "Please check syntax: no argument is expected.";
	public static final String MSG_ERROR_INVALID_TASK_NUMBER = "Please check task number.";
	public static final String MSG_ERROR_WRONG_DATE = "Wrong date format";

	// Others

	public static final String TOBEDONE = "2BeDone";
	public static final String EXIT_TOBEDONE = "exit";
	public static final String TASK = "Task ";
	public static final String TIME_ZONE = "UTC";
	public static final String TIME_ZONE_GMT = "Etc/GMT";
	public static final String NOTFOUND = " not found.";

	public static final int COMMAND_WINDOW_SIZE = 5;
	public static final int TIMER_PERIOD = 2 * 1000;
	public static final int TABLE_ROW_MAX_COUNT = 100;
	public static final int TIME_INTERVAL = 15;
	public static final int NUM_OF_DAYS_TO_POSTPONE = 4;

	public static final int TWO = 2;
	public static final int ONE_LOOKAHEAD = 1;
	public static final int TWEVEL = 12;
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

	// GoogleSync
	public static final String MESSAGE_SYNC_SUCCESS = "Successfully synchronized.";
	public static final String SERVER_UNAVAILABLE = "Cannot connect to Google server now.";
	public static final String MSG_GCAL_NOT_AUTHORIZED = "Authentication erroe.";
	public static final int NEWLY_ADD_CALENDAR_ID = 1;
	// Storage
	public static final String MESSAGE_FAILED_TO_WRITE_TO_FILE = "Failed to write to file.";
	public static final String MESSAGE_FAILED_TO_READ_FROM_FILE = "Failed to read from file.";
	public static final String MESSAGE_FAILED_TO_PARSE_DATE = "Failed to parse date.";
	public static final String MESSAGE_STORE_SUCCESSFUL = "Successfully stored tasks.";
	public static final String MESSAGE_TASK_LIST_NULL = "Store aborted, list of tasks is null";
	public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
	


}
