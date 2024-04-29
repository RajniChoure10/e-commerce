package com.dollop.app.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderAddressValidator implements ConstraintValidator<OrderAddressValid, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}
	  private static boolean isNotEmpty(String str) {
	        return str != null && !str.trim().isEmpty();
	    }
	
	public static void main(String[] args) {
        String street = "123 Main St";
        String city = "Anytown";
        String state = "CA";
        String country = "USA";

        if(isValid1(street, city, state, country)) {
            System.out.println("Valid billing address");
        } else {
            System.out.println("Invalid billing address");
        }
    }

	private static boolean isValid1(String street, String city, String state, String country) {
		 return isNotEmpty(street) && isNotEmpty(city) && isNotEmpty(state) && isNotEmpty(country);
    	}

}
