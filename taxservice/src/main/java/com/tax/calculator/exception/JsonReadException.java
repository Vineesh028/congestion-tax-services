package com.tax.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when city json read fails
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonReadException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JsonReadException(String message) {
		super(message);
	}
}
