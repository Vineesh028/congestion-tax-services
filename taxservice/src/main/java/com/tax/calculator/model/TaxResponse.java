package com.tax.calculator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Sent when tax request is successful
 */
@Schema(name = "TaxResponse", description = "Schema to hold successful response information")
@Data
@AllArgsConstructor
public class TaxResponse {

	private String statusCode;

	private String statusMessage;

}
