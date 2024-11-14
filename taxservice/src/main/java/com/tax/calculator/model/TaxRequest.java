package com.tax.calculator.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * API request for tax calculation
 */
@Schema(name = "TaxRequest", description = "Schema to hold tax request information")
@Data
public class TaxRequest {

	@NotNull(message = "Vehicle can not be null")
	@Valid
	Vehicle vehicle;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotEmpty(message = "Dates can not be a null or empty")
	List<LocalDateTime> dates;

}
