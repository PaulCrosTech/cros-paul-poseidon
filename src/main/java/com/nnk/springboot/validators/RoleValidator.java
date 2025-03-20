package com.nnk.springboot.validators;


import com.nnk.springboot.validators.annotations.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * RoleValidator
 */
public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private String pattern;

    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidRole constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }


    /**
     * Check if the role is valid
     *
     * @param role                       the role
     * @param constraintValidatorContext the context
     * @return true if the role is valid
     */
    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        return role != null && role.matches(pattern);
    }
}
