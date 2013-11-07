package com.tobedone.exception;

/**
 * @author A0117215
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class contains the new exception when the command is not defined
 * 
 * 
 */
public class CommandNotFoundException extends Exception{
	
	// @author A0117215R
	public CommandNotFoundException() {
		super();
	}

	// @author A0117215R
	public CommandNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
}
