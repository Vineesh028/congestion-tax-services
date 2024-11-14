package com.tax.calculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for checking a string contains white spaces
 */
public class NoSpacesValidator implements ConstraintValidator<NoSpaces, String> {
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.trim().equals(value);
    }
}