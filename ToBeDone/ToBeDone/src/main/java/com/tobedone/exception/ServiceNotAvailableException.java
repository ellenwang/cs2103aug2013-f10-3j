package com.tobedone.exception;

/**
 * @author A0118248
 * @version 0.4
 * @since 4-11-2012
 * 
 *        This class contains the new exception when we cannot connect to Google
 * 
 */
public class ServiceNotAvailableException extends Exception {
	// @author A0104167M
	public ServiceNotAvailableException(String errorMessage) {
		super(errorMessage);
	}
}
