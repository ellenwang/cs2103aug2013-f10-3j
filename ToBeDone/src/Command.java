import java.util.Vector;

public class Command {
	private String commandType;
	private Vector<String> commandParameters;

	public Command(String commandType, Vector<String> commandParameters) {
		this.commandType = commandType;
		this.commandParameters = commandParameters;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public Vector<String> getCommandParameters() {
		return commandParameters;
	}

	public void setCommandParameters(Vector<String> commandParameters) {
		this.commandParameters = commandParameters;
	}
}
