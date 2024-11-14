package com.tax.calculator.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tax.calculator.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handler for invalid request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> validationErrors = new HashMap<>();
		List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

		validationErrorList.forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String validationMsg = error.getDefaultMessage();
			validationErrors.put(fieldName, validationMsg);
		});
		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler for generic exception
	 * 
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
		ErrorResponse errorResponseDTO = new ErrorResponse(webRequest.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handler for VehicleTypeNotFoundException
	 * 
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(VehicleTypeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleVehicleTypeNotFoundException(VehicleTypeNotFoundException exception,
			WebRequest webRequest) {
		ErrorResponse errorResponse = new ErrorResponse(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,
				exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler for JsonReadException
	 * 
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(JsonReadException.class)
	public ResponseEntity<ErrorResponse> handleJsonReadException(JsonReadException exception, WebRequest webRequest) {
		ErrorResponse errorResponse = new ErrorResponse(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,
				exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}