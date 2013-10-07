import java.util.Scanner;


public class TobeDoneUI {
	
	private static final String WELCOME_MESSAGE = "Now is ToBeDone moment";
	private static final String CONFIRM_MESSAGE = "Are you sure to do this?";

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(WELCOME_MESSAGE);
		TobeDoneUI.readCommand();
		
		
		
	}
	
	static void showToUser(String message){
		System.out.println(message);
	}
	
	
	static void readCommand() {
		String comString;
		Scanner scanner = new Scanner(System.in);
		
		comString = scanner.nextLine();
		TobeDoneUI.commandParser(comString);
		
		scanner.close();
	}
	
	
	static void commandParser(String comString){
		Logic.createTask(comString);
	}
	
	
	static boolean isConfirm(){
		boolean confirmInfo;
		showToUser(CONFIRM_MESSAGE);
		
		confirmInfo=TobeDoneUI.getConfirmation();
		return confirmInfo;
	}
	
	static boolean getConfirmation(){
		String yesOrNO;
		boolean comfirmation = false;
		
		Scanner scanner = new Scanner(System.in);	
		yesOrNO = scanner.nextLine();
		scanner.close();
		
		switch (yesOrNO) {
		case "yes": comfirmation = true; break;
		case "no" : comfirmation = false; break;
		}
		
		return comfirmation;
	}
	
}
