package com.example.paymentgateway.vault.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ExpiryYearValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ExpiryYear {
	
	String message() default "Expiry year cannot be in Past";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
