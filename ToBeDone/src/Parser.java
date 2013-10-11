//import java.util.Scanner;
import java.util.Vector;


public class Parser {
	static final String WRONG_DESCRIPTION = "Wrong description format!";
	
//	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		String string = scanner.nextLine();
//		try {
//			parseCommand(string);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static Command parseCommand(String commandString) throws Exception {
		String commandType = getComType(commandString);
		try {
			Vector<String> parameters = getComParas(commandString);
			Command command = new Command(commandType, parameters);
			return command;
		} catch (Exception e) {
			throw e;
		}
	}
		
	private static Vector<String> getComParas(String commandString) throws Exception {
		Vector<String> parameters = new Vector<String>(); 
		String comParaString = getComParaString(commandString);
		
		if(comParaString == null){
			return null;
		}
		
		if (comParaString.startsWith("\"")) {
			String description = getDescription(comParaString);
			if (description != null) {
				parameters.add(description);
			}
		
		String description = getDescription(comParaString);
			
		if(description != null){
			if (description.equals(WRONG_DESCRIPTION)) {
				Exception exception = new Exception(WRONG_DESCRIPTION);
				throw exception;
			}
			parameters.add(description);
		}
		
			String[] parasExceptDes = getComParasExceptDes(comParaString);
			for (int i = 0; i < parasExceptDes.length; i++) {
				parameters.add(parasExceptDes[i]);
			}
		} else {
			parameters.add(comParaString);
		}
		
		return parameters;	
	}
	
	protected static String[] getComParasExceptDes(String comParaString) {
		int indexOfDes = comParaString.lastIndexOf('\"');
		String[] parasArray;

		// parasExceptDes contains StartTime/EndTime and
		// priority except description
		if (indexOfDes == -1) {
			String parsWithoutDes = comParaString.substring(indexOfDes + 1);
			parasArray = parsWithoutDes.split(" ");
		} else {
			String parsWithoutDes = comParaString.substring(indexOfDes + 2);
			parasArray = parsWithoutDes.split(" ");
		}
		return parasArray;
	}
	
	private static String getDescription(String comParaString) {
		int indexOfDes = comParaString.lastIndexOf('\"');
		if (indexOfDes == -1) {
			return null;
		}
		if(indexOfDes == 0){
			return WRONG_DESCRIPTION;
		}
		if(!comParaString.startsWith("\"")){
			return WRONG_DESCRIPTION;
		}
		
		String description = comParaString.substring(1,indexOfDes);
		return description;
	}	
	
	private static String getComParaString(String commandString) {
		String comParaString = null;
		int index = 0;
		int length = commandString.length();

		while (index < length && commandString.charAt(index) != ' ') {
			index++;
		}
		// divide at the first blank space
		if (index == commandString.length()) {
			return null;
		}

		comParaString = commandString.substring(index + 1);
		return comParaString;
	}
	
	private static String getComType(String commandString) {
		String comType = null;
		int index = 0;
		int length = commandString.length();

		while (index < length && commandString.charAt(index) != ' ') {
			index++;
		}
		// divide at the first blank space
		comType = commandString.substring(0, index);
		return comType;
	}
}
