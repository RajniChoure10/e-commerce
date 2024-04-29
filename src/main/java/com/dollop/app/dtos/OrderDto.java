package com.dollop.app.dtos;

import java.util.Date;
import com.dollop.app.validate.BillingNameValid;
import com.dollop.app.validate.OrderAddressValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {
   
	private String orderId;
	
	@NotBlank
    private String orderStatus;
	

    private Double orderAmount;
	
	@NotBlank
    private String paymentStatus;
	
	@NotBlank
	@OrderAddressValid
    private String bilingAddress;
    
	@Pattern(regexp = "^(?:\\+?\\d{2}[\\s-]?)?(?:\\d{10})$",
			message = "Invalid mobile number")
	@NotBlank(message = "phone number is required")
	@NotBlank(message = "phone number is required")
    private String bilingPhone;
    
    @NotBlank
    @BillingNameValid(message="Billing Name Invalid")
    private String bilingName;
    private Date orderDate;
    private Date deliverDate;
}
