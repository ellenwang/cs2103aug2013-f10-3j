package com.tobedone.exception;

/**
 * @author A0118248A
 * @version 0.5
 *     This class contains the new exception when we cannot connect to Google
 * 
 */
//@author A0118248A
public class ServiceNotAvailableException extends Exception {
	public ServiceNotAvailableException(String errorMessage) {
		super(errorMessage);
	}
}
