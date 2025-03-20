package com.nnk.springboot.validators.annotations;

import com.nnk.springboot.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidUsername {

    /**
     * message
     *
     * @return String
     */
    String message() default "The username must be between 3 and 125 characters long (letters, digits and point).";

    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "^[.a-zA-Z0-9]{3,125}$";

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
