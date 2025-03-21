package com.nnk.springboot.validators.annotations;


import com.nnk.springboot.validators.NotNullAndPositiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidNotNullAndPositive
 */
@Constraint(validatedBy = NotNullAndPositiveValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidNotNullAndPositive {

    /**
     * message
     *
     * @return String
     */
    String message() default "The value must be positive and not be null.";

    /**
     * groups
     *
     * @return Class
     */
    Class<?>[] groups() default {};

    /**
     * payload
     *
     * @return Class
     */
    Class<? extends Payload>[] payload() default {};
}
