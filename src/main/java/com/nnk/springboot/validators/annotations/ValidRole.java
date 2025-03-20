package com.nnk.springboot.validators.annotations;

import com.nnk.springboot.validators.RoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidRole {

    /**
     * message
     *
     * @return String
     */
    String message() default "The role must be USER or ADMIN.";

    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "(USER|ADMIN)";

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
