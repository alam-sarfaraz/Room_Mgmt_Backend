package com.inn.customException;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorMessage {

	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timeStamp;

	public ErrorMessage(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.timeStamp = timeStamp;
	}

}
