package com.tobedone.ui;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 */
public class TextUIAtd {
	
	//System test.
	@Test
	public void testExecuteCommands() throws Exception {
		TextUI.setCommandString("clear");
		TextUI.executeCommands();
		
		TextUI.setCommandString("add go to school by today");
		TextUI.executeCommands();
		assertEquals("Added task: go to school \n\tdeadline: 23:59,11/11", TextUI.getCommandExecuteResult().getFeedback());
		
		TextUI.setCommandString("add attend tutorial from today to tomorrow ");
		TextUI.executeCommands();
		assertEquals("Added task: attend tutorial \n\tfrom: 00:01,11/11\n\tto:   23:59,12/11", TextUI.getCommandExecuteResult().getFeedback());
	}
	
	
}
