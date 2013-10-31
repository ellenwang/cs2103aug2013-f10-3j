package Command;

import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import Storage.Storage;
import TaskItem.DeadlineTaskItem;
import TaskItem.FloatingTaskItem;
import TaskItem.TaskItem;
import TaskItem.TimedTaskItem;

public class CreateCommand extends Command {

	public CreateCommand(String commandType, Vector<String> commandParameters) {
		super("create", commandParameters);
	}

	private boolean redoable = true;
	private boolean undoable = true;
	private static final String MESSAGE_DELETE_SUCCESSFUL = "Deleted task: %1$s";
	private static final String MESSAGE_CREATE_SUCCESSFUL = "Created task: %1$s";

	public boolean isRedoable() {
		return redoable;
	}

	public void setRedoable(boolean redoable) {
		this.redoable = redoable;
	}

	public boolean isUndoable() {
		return undoable;
	}

	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}

	public String undo() {
		lastDeletedTask = allTasks.remove(lastCreatedTask.getTaskID());
		updateTaskIDs();
		Storage.store(allTasks);
		String feedback = String.format(MESSAGE_DELETE_SUCCESSFUL,
				lastCreatedTask);
		return feedback;
	}

	public String redo() {
		allTasks.add(lastCreatedTask.getTaskID(), lastCreatedTask);
		updateTaskIDs();
		Storage.store(allTasks);
		String feedback = String.format(MESSAGE_CREATE_SUCCESSFUL,
				lastCreatedTask);
		return feedback;
	}

	public void execute() {
		// / since there are 3 kind of task. each has different number of
		// parameters
		Vector<String> Para = command.getCommandParameters();
		int taskType = Para.size();
		TaskItem newItem = new TaskItem();

		String taskDescription = Para.get(0);
		Date taskStartTime = null;
		Date taskEndTime = null;
		int priority = 1;

		// a floating task, just has description and priority
		if (taskType == FLOATING_TASK) {
			priority = Integer.parseInt(Para.get(1));
			newItem = new FloatingTaskItem(taskDescription, priority);
		}

		// a task just has description\endTime and priority
		if (taskType == DEADLINE_TASK) {
			try {
				taskEndTime = simpleDateFormat
						.parse(Para.get(1) + CURRENT_YEAR);
			} catch (ParseException e) {
				return MESSAGE_WRONG_TIME_FORMAT;
			}
			priority = Integer.parseInt(Para.get(2));
			newItem = new DeadlineTaskItem(taskDescription, priority,
					taskEndTime);
		}

		// a full task with description\startTime\endTime and priority
		if (taskType == TIMED_TASK) {
			try {
				taskStartTime = simpleDateFormat.parse(Para.get(1)
						+ CURRENT_YEAR);
				taskEndTime = simpleDateFormat
						.parse(Para.get(2) + CURRENT_YEAR);
			} catch (ParseException e) {
				return MESSAGE_WRONG_TIME_FORMAT;
			}

			if (taskEndTime.before(taskStartTime)) {
				return MESSAGE_ENDTIME_SMALLER_THAN_STARTTIME;
			}

			priority = Integer.parseInt(Para.get(3));
			newItem = new TimedTaskItem(taskDescription, priority,
					taskStartTime, taskEndTime);
		}

		// a create command has 3 parameters at most
		if (taskType > TIMED_TASK) {
			return MESSAGE_TOO_MANY_PARAMETERS;
		}

		allTasks.add(newItem);
		updateTaskIDs();
		Storage.store(allTasks);
		lastModifyingCommand = command;
		lastCreatedTask = newItem;
		return String.format(MESSAGE_CREATE_SUCCESSFUL, newItem);
	}
}
