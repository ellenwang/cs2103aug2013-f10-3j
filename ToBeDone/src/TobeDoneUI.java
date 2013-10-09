import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TobeDoneUI {

	private static final String WELCOME_MESSAGE = "Now is ToBeDone moment";
	private static final String NO_SUCH_COMMAND = "No such command!";
	private static final String WRONG_COMMAND_FORMAT = "Please follow the correct command format!";
	private static final String WRONG_TIME_FORMAT = "Please follow the time format as indicated!";
	private static final String WRONG_TASK_DESCRIPTION_FORMAT = "Please double quote the task description!";
	private static final String ENDTIME_SMALLER_THAN_STARTTIME = "The end time of tasks can't be smaller than start time";
	private static final String WRONG_REDO = "a redo command must follow an undo command!";
	private static final String MEANINGLESS_UNDO = "Meaningless to undo Search/View";
	private static final String INVALID_UNDO = "No command can undo!";

	private static final int FLOATING_TASK = 1;
	private static final int DEADLINE_TASK = 2;
	private static final int TIMED_TASK = 3;

	private static final int TASK_PRIORITY_HIGH = 3;
	private static final int TASK_PRIORITY_MEDIUM = 2;
	private static final int TASK_PRIORITY_LOW = 1;

	private static String lastCommandString = null;

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"MM-dd'at'HH:mm");

	public static void main(String[] args) {
		showToUser(WELCOME_MESSAGE);
		readCommand();
	}

	static void showToUser(String message) {
		System.out.println(message);
	}

	static void readCommand() {
		boolean isExit = false;
		Scanner scanner = new Scanner(System.in);

		while (!isExit) {
			if (scanner.hasNext()) {
				String comString = scanner.nextLine();
				commandParser(comString);
				if (comString != "undo") {
					lastCommandString = comString;
				}
			}
		}
		scanner.close();
	}

	static void commandParser(String comString) {
		String comType;
		String comPara;

		comType = getComType(comString);
		comPara = getComPara(comString);

		switch (comType) {
		case "create":
			analyseCreate(comPara);
			break;
		case "delete":
			analyseDelete(comPara);
			break;
		case "view":
			analyseView(comPara);
			break;
		case "search":
			analyseSearch(comPara);
			break;
		case "undo":
			analyseUndo(comPara);
			break;
		case "finish":
			analyseFinish(comPara);
			break;
		case "redo": {
			if (!lastCommandString.equals("undo")) {
				showToUser(WRONG_REDO);
				break;
			} else {
				analyseRedo(comPara);
			}
		}
			break;
		default:
			showToUser(NO_SUCH_COMMAND);
		}
	}

	static String getComType(String comString) {
		String comType;

		int index = 0;
		int length = comString.length();

		while (index < length && comString.charAt(index) != ' ') {
			index++;
		}
		// divide at the first blank space
		comType = comString.substring(0, index);

		return comType;
	}

	static String getComPara(String comString) {
		String comPara;

		int index = 0;
		int length = comString.length();

		while (index < length && comString.charAt(index) != ' ') {
			index++;
		}
		// divide at the first blank space
		if (index == comString.length()) {
			return null;
		}

		comPara = comString.substring(index + 1);

		return comPara;
	}

	static void analyseCreate(String comPara) {

		String userMessage;

		try {
			String taskDescription = null;
			Date taskStartTime = null;
			Date taskEndTime = null;
			int priority = TASK_PRIORITY_LOW;

			// the location of the last right quotation, which means the end of
			// take description
			int indexOfDes = 0;

			for (int i = 0; i < comPara.length(); i++) {
				if (comPara.charAt(i) == '"')
					indexOfDes = i;
			}
			if (indexOfDes == 0) {
				showToUser(WRONG_TASK_DESCRIPTION_FORMAT);
				return;
			}

			taskDescription = comPara.substring(1, indexOfDes);

			// taskParasExceptDes contains taskStartTime/taskEndTime and
			// priority except description
			String taskStringExceptDes = comPara.substring(indexOfDes + 2);
			String[] taskParasExceptDes = taskStringExceptDes.split(" ");

			// since there are 3 kind of task. each has different number of
			// parameters
			int taskType = taskParasExceptDes.length;

			// a floating task, just has description and priority
			if (taskType == FLOATING_TASK) {
				priority = Integer.parseInt(taskParasExceptDes[0]);
			}

			// a task just has description\endTime and priority
			if (taskType == DEADLINE_TASK) {
				try {
					taskEndTime = simpleDateFormat.parse(taskParasExceptDes[0]);
				} catch (ParseException e) {
					showToUser(WRONG_TIME_FORMAT);
					return;
				}
				priority = Integer.parseInt(taskParasExceptDes[1]);
			}

			// a full task with description\startTime\endTime and priority
			if (taskType == TIMED_TASK) {
				try {
					taskStartTime = simpleDateFormat
							.parse(taskParasExceptDes[0]);
					taskEndTime = simpleDateFormat.parse(taskParasExceptDes[1]);
				} catch (ParseException e) {
					showToUser(WRONG_TIME_FORMAT);
					return;
				}

				if (taskEndTime.before(taskStartTime)) {
					showToUser(ENDTIME_SMALLER_THAN_STARTTIME);
					return;
				}

				priority = Integer.parseInt(taskParasExceptDes[2]);
			}

			// a create command has 3 parameters at most
			if (taskType > TIMED_TASK) {
				showToUser(WRONG_COMMAND_FORMAT);
				return;
			}

			userMessage = Logic.createTask(taskDescription, taskStartTime,
					taskEndTime, priority);
			showToUser(userMessage);

		} catch (Exception e) {
			showToUser(WRONG_COMMAND_FORMAT);
			return;
		}
	}

	static void analyseDelete(String comPara) {
		String useMessage;
		try {
			int taskIndex;
			taskIndex = Integer.parseInt(comPara);
			useMessage = Logic.deleteTask(taskIndex);
			showToUser(useMessage);
		} catch (Exception e) {
			showToUser(WRONG_COMMAND_FORMAT);
		}

	}

	static void analyseView(String comPara) {
		String viewResult = null;

		// read a specified task
		if (comPara.length() == 1) {
			try {
				int taskIndex;
				taskIndex = Integer.parseInt(comPara);
				viewResult = Logic.viewTask(taskIndex);
			} catch (Exception e) {
				showToUser(WRONG_COMMAND_FORMAT);
			}
		}

		// read a list of task
		switch (comPara) {
		case "all":
			viewResult = Logic.viewAll();
			break;
		case "finished":
			viewResult = Logic.viewFinished();
			break;
		case "unfinished":
			viewResult = Logic.viewUnfinished();
			break;
		default:
			showToUser(WRONG_COMMAND_FORMAT);
			break;
		}

		showToUser(viewResult);
	}

	static void analyseSearch(String comPara) {
		String searchResult;
		searchResult = Logic.searchTask(comPara);
		showToUser(searchResult);
	}

	static void analyseUndo(String comPara) {
		String userMessage = null;

		if (lastCommandString == null) {
			showToUser(INVALID_UNDO);
			return;
		}
		String lastComType = getComType(lastCommandString);

		if (lastComType.equals("delete") || lastComType.equals("create")
				|| lastComType.equals("finish") || lastComType.equals("redo")) {
			userMessage = Logic.undo();
		} else {
			showToUser(MEANINGLESS_UNDO);
		}

		showToUser(userMessage);
	}

	static void analyseRedo(String comPara) {
		String userMessage;
		userMessage = Logic.redo();
		showToUser(userMessage);
	}

	static void analyseFinish(String comPara) {

		// read a specified task
		if (comPara.length() == 1) {
			try {
				int taskIndex;
				taskIndex = Integer.parseInt(comPara);
				Logic.markTask(taskIndex);
			} catch (Exception e) {
				showToUser(WRONG_COMMAND_FORMAT);
			}
		}
	}

	static String getLastCommandType() {
		return getComType(lastCommandString);
	}

}
