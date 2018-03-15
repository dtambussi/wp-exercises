package com.exercise.controller.error;

import static org.springframework.core.annotation.AnnotatedElementUtils.*;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
		ex.printStackTrace();
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), ""), resolveStatus(ex));
	}
	
	private HttpStatus resolveStatus(final Exception ex) {
		final ResponseStatus annotation = findMergedAnnotation(ex.getClass(), ResponseStatus.class);
		return annotation != null ? annotation.value() : HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
