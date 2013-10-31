package Command;

import java.util.Vector;

public class ViewCommand extends Command{
	public ViewCommand(String commandType, Vector<String> commandParameters) {
		super("view", commandParameters);
	}
	private boolean redoable = true;
	private boolean undoable = false;

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
