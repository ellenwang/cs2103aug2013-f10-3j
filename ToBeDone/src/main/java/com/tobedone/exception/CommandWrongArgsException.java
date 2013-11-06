package com.tobedone.exception;

/**
 * @author A0117215
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class contains the new exception when the command agrs are wrong
 * 
 * 
 */
public class CommandWrongArgsException extends Exception{
	
	// @author A0117215R
	public CommandWrongArgsException() {
		super();
	}

	// @author A0117215R
	public CommandWrongArgsException(String errorMessage) {
		super(errorMessage);
	}
	
}