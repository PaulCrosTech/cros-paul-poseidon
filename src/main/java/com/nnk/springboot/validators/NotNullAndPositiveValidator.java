package com.nnk.springboot.validators;

import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
import jakarta.validation.ConstraintValidator;

/**
 * NotNullAndPositiveValidator Class
 */
public class NotNullAndPositiveValidator implements ConstraintValidator<ValidNotNullAndPositive, Double> {

    /**
     * Check if the value is not null and positive
     *
     * @param value   the value to check
     * @param context the context
     * @return true if the value is not null and positive
     */
    @Override
    public boolean isValid(Double value, jakarta.validation.ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
