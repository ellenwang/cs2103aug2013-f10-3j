package com.tobedone.exception;

public class TaskNotExistException extends Exception {
	// @author A0105682h
	public TaskNotExistException() {
		super();
	}

	// @author A0105682h
	public TaskNotExistException(String errorMessage) {
		super(errorMessage);
	}
}
