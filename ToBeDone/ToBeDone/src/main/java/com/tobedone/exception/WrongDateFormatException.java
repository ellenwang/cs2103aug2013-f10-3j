package com.tobedone.exception;

/**
 * @author A0117215
 * @version 0.5
 * @since 09-01-2013
 * 
 *        This class contains the new exception when the date format is wrong
 * 
 * 
 */

public class WrongDateFormatException extends Exception {
	// @author A0117215R
	
	public WrongDateFormatException() {
		super();
	}

	// @author A0117215R
	public WrongDateFormatException(String errorMessage) {
		super(errorMessage);
	}
}
