package Command;

import java.util.Vector;

public class DeleteCommand extends Command{
	public DeleteCommand(String commandType, Vector<String> commandParameters) {
		super("delete", commandParameters);
	}
	private boolean redoable = true;
	private boolean undoable = true;

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
}
