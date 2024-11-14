package com.tax.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when request vehicle type does not exist
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VehicleTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VehicleTypeNotFoundException(String message) {
		super(message);
	}

}
