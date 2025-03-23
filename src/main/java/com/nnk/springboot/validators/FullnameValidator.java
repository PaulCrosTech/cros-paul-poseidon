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


    /**
     * Check if the fullname is valid
     *
     * @param fullname                   the fullname
     * @param constraintValidatorContext the context
     * @return true if the fullname is valid
     */
    @Override
    public boolean isValid(String fullname, ConstraintValidatorContext constraintValidatorContext) {
        return fullname != null && fullname.matches(pattern);
    }
}
