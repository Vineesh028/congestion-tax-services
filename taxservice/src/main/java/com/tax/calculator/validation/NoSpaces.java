package com.tax.calculator.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validation annotation for no spaces in string
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoSpacesValidator.class)
public @interface NoSpaces {

	String message() default "The value must not contain white spaces";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
