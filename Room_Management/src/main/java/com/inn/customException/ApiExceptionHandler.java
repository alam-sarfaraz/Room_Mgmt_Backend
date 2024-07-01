package com.inn.customException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = { CustomException.class })
	public ResponseEntity<Object> handleApiException(CustomException e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}

}
