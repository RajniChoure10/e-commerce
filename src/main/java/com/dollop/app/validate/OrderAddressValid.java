package com.dollop.app.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = OrderAddressValidator.class)
public @interface OrderAddressValid {

	 String message() default "Invalid order address ";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
