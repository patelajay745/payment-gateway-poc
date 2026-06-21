package com.example.paymentgateway.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Money {
	
	private int amountUnits;
	
	private String currency;
	
	public static Money of(int amountUnits, String currency) {
		return new Money(amountUnits, currency);
	}
	
	public static Money usd(int amountUnits) {
		return new Money(amountUnits, "USD");
	}
	
	public Money add(Money money) {
		if (!this.currency.equals(money.currency)) {
			throw new IllegalArgumentException("Currency has wrong value");
		}
		
		return new Money(this.amountUnits + money.amountUnits, this.currency);
	}
	
	public Money subtract(Money money) {
		if (!this.currency.equals(money.currency)) {
			throw new IllegalArgumentException("Currency has wrong value");
		}
		
		return new Money(this.amountUnits - money.amountUnits, this.currency);
	}
}
