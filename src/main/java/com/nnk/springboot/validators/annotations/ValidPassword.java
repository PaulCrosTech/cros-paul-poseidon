package com.nnk.springboot.validators.annotations;


import com.nnk.springboot.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidPassword Annotation
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPassword {

    /**
     * message
     *
     * @return String
     */
    String message() default "The password must be between 8 and 20 characters long. It must contain at least one digit, one lowercase letter, one uppercase letter, one special character (@#$%^&+=).";


    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

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
