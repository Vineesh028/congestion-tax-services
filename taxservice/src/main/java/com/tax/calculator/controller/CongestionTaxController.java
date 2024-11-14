package com.tax.calculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tax.calculator.model.ErrorResponse;
import com.tax.calculator.model.TaxRequest;
import com.tax.calculator.model.TaxResponse;
import com.tax.calculator.service.TaxService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "REST APIs for congestion tax services", description = "REST APIs to calculate congestion taxes for vehicles")
@RestController
@RequestMapping(path = "/congestion-tax", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
public class CongestionTaxController {

	private TaxService taxService;

	/**
	 * Calling service method to calculate tax from tax request
	 * @param taxRequest
	 * @return ResponseEntity<TaxResponse>
	 */
	@Operation(summary = "Calcuate Congestion Tax REST API", description = "REST API to calculate congestion tax")
	@ApiResponse(responseCode = "200", description = "HTTP Status Calculated")
	@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping()
	public ResponseEntity<TaxResponse> calculateCongestionTax(@Valid @RequestBody TaxRequest taxRequest) {

		TaxResponse taxResponse = taxService.calculateTax(taxRequest);

		return ResponseEntity.status(HttpStatus.OK).body(taxResponse);

	}

}
