package com.dollop.app.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class BillingNameValidation implements ConstraintValidator<BillingNameValid, String>{

	// Regular expression to match letters, spaces, hyphens, and apostrophes
    private static final String VALID_NAME_REGEX = "^[a-zA-Z\\s'-]+$";

    @Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
    	if(value != null && VALID_NAME_REGEX.matches(value))
    	    return true;
    	else {
    		return false;
    	}

	} 
}

	



