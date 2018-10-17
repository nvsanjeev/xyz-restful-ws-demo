package com.xyz.cta.rest.ws.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactZipCodeValidator.class)
@Documented
public @interface ValidZipCode {
    String message() default "State: ${validatedValue.state}, city: ${validatedValue.city} and zipcode: ${validatedValue.zipCode} do not match ";

    //"Found: ${validatedValue.totalPrice}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}