import java.util.Scanner;

public class TextUI {
	// messages to the user
	private static final String MESSAGE_WELCOME = "Welcome to ToBeDone!";

	// scanner to read input from the user
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		showToUser(MESSAGE_WELCOME);
		readAndExecuteCommands();
	}

	private static void showToUser(String message) {
		System.out.println(message);
	}

	private static void readAndExecuteCommands() {
		String input = "";
		while (!input.equals("exit")) {
			try {
				input = readUserInput();
				Command command = Parser.parseCommand(input);
				String feedback = Logic.executeCommand(command);
				showToUser(feedback);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private static String readUserInput() {
		String input = scanner.nextLine();
		return input;
	}

}
