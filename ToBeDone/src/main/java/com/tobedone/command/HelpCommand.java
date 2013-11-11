//@author A0105682H
package com.tobedone.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.tobedone.utils.Constants;
import com.tobedone.utils.LogMessages;

/**
 * @author A0105682H
 * @version 0.5
 * @since 6-11-2013
 * 
 *        This class handles help command and reads from the text file.
 * 
 */
public class HelpCommand extends Command {

	// Constructors
	public HelpCommand() {
		super();
		isUndoable = false;
	}

	@Override
	/**
	 * Read from the help sheet and passes to UI.
	 */
	protected void executeCommand() {
		logger.info(LogMessages.INFO_HELP);
		String output = Constants.EMPTY_STRING;
		String line;
		try {
			InputStream is = HelpCommand.class
					.getResourceAsStream(Constants.HELP_PATH);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			line = br.readLine();
			while (line != null) {
				output += line + Constants.NEWLINE;
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			feedback = Constants.EMPTY_STRING;
		}
		feedback = output;
	}

}
