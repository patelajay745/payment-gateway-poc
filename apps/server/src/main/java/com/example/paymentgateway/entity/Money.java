package com.example.paymentgateway.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Money {
	
	private final int amountUnits;
	
	private final String currency;
	
	private Money(int amountUnits, String currency) {
		this.amountUnits = amountUnits;
		this.currency = currency;
	}
	
	public Money of(int amountUnits, String currency) {
		
		return new Money(amountUnits, currency);
	}
	
	public Money usd(int amountUnits) {
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
