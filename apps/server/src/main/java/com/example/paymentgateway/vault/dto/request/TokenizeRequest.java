package com.example.paymentgateway.vault.dto.request;

import com.example.paymentgateway.vault.validation.ExpiryYear;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.LuhnCheck;

import java.util.UUID;

public record TokenizeRequest(
		
		@NotBlank(message = "PAN is required")
		@LuhnCheck(message = "Invalid card number")
		@Pattern(regexp = "^[0-9]{13,19}$", message = "PAN length is invalid")
		String pan,
		
		@NotBlank(message = "cvv is required")
		@Pattern(regexp = "^[0-9]{3,4}$", message = "CVV length is invalid")
		String cvv,
		
		@NotBlank(message = "Expiry month is required")
		@Min(value = 1, message = "Expiry month must be between 1 to 12")
		@Max(value = 12, message = "Expiry month must be between 1 to 12")
		Integer expiryMonth,
		
		@NotNull(message = "Expiry year is required")
		@ExpiryYear(message = "Expiry year must not be in the past")
		Integer expiryYear,
		
		UUID customerId,
		
		@NotBlank(message = "Card holder name is required")
		@Min(value = 3, message = "Card holder name must be between 1 to 12")
		String cardHolderName


) {

}
