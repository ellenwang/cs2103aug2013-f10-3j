package Command;

import java.util.Vector;

public class UpdateCommand extends Command{
	public UpdateCommand(String commandType, Vector<String> commandParameters) {
		super("update", commandParameters);
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
