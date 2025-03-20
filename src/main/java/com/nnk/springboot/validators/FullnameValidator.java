package com.nnk.springboot.validators;

import com.nnk.springboot.validators.annotations.ValidFullname;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * FullnameValidator Class
 */
public class FullnameValidator implements ConstraintValidator<ValidFullname, String> {

    private String pattern;

    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidFullname constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }


    @Override
    public boolean isValid(String fullname, ConstraintValidatorContext constraintValidatorContext) {
        return fullname != null && fullname.matches(pattern);
    }
}
