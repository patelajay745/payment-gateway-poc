package com.example.paymentgateway.payment.config;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.processor.PaymentProcessor;
import com.example.paymentgateway.payment.processor.strategy.CardPaymentProcessor;
import com.example.paymentgateway.payment.processor.strategy.NetBankingPaymentProcessor;
import com.example.paymentgateway.payment.processor.strategy.UpiPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {
	
	@Bean
	public Map<PaymentMethod, PaymentProcessor> PaymentProcessorMap() {
		return Map.of(PaymentMethod.CARD, new CardPaymentProcessor(),
				PaymentMethod.NETBANKING, new NetBankingPaymentProcessor(),
				PaymentMethod.UPI, new UpiPaymentProcessor());
	}
}
