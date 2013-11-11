package com.tobedone.exception;

/**
 * Handles the exception if the task does not exist.
 * @author A0105682H
 *
 */
public class TaskNotExistException extends Exception {
	public TaskNotExistException() {
		super();
	}

	public TaskNotExistException(String errorMessage) {
		super(errorMessage);
	}
}
