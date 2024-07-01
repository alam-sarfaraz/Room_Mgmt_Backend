package com.inn.customException;

public class CustomException extends Exception {

	private static final long serialVersionUID = -4486964619294135195L;

	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

}
