package com.nnk.springboot.validators.annotations;

import com.nnk.springboot.validators.FullnameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = FullnameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidFullname {

    /**
     * message
     *
     * @return String
     */
    String message() default "The fullname must be between 3 and 125 characters.";

    /**
     * pattern
     *
     * @return String
     */
    String pattern() default ".{3,125}";

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
