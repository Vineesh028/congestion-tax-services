package com.tax.calculator.model;

import com.tax.calculator.validation.NoSpaces;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(name = "Vehicle", description = "Schema to hold vehicle information")
@Data
public class Vehicle {

	@NotEmpty(message = "Vehicle Type can not be a null or empty")
	@NoSpaces
	private String vehicleType;

}
