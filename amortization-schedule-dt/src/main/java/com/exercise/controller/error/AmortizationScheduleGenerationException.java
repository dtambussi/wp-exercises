package com.exercise.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AmortizationScheduleGenerationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AmortizationScheduleGenerationException() { }

	public AmortizationScheduleGenerationException(final String message) {
		super(message);
	}

	public AmortizationScheduleGenerationException(final String message, Throwable cause) {
		super(message, cause);
	}
}
