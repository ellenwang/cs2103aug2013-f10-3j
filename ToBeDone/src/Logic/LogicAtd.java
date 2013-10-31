package Logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import Command.Command;

public class LogicAtd {
public static final String COMMAND_INVALID_ERROR = "The command is invalid";
	
	public static final String TESTING_FILE_NAME = "test.txt";
	public static final File TEST_FILE = new File(TESTING_FILE_NAME);
	

    /*test search if it search only the description or all the task string*/
	@Test
	public void test() throws IOException {
		File file = new File("mytest.txt");
		Vector<String> paras = new Vector<String>();
		paras.clear();
		paras.add("all");
		Logic.executeCommand(new Command ("delete", paras));

		assertEquals("", Logic.executeCommand(new Command("search", paras)));
		
		//Test task with deadline
		paras.clear();
		paras.add("checkpoint V0.2");
		paras.add("25/10,10:00");
		paras.add("1");
		Logic.executeCommand(new Command("create", paras));
		
		//Test a periodical task
		paras.clear();
		paras.add("se tutorial");
		paras.add("25/10,10:00");
		paras.add("25/10,11:00");
		paras.add("1");
		Logic.executeCommand(new Command("create", paras));
		
		//Test a floating task
		paras.clear();
		paras.add("Dragonboating CCA");
		paras.add("2");
		Logic.executeCommand(new Command("create", paras));
		
		String wordToSearch = "tutorial";
		paras.clear();
		paras.add(wordToSearch);	
		assertEquals("test case for searchKeyword method ",
				"1. se tutorial	 starts from: 25/10,10:00	 ends at: 25/10,11:00",
				Logic.executeCommand(new Command("search", paras)));

		wordToSearch = "difficult";
		paras.clear();
		paras.add(wordToSearch);
		assertEquals("\"difficult\" is not found in this file.",
				Logic.executeCommand(new Command("search", paras)));
	}

	
}
