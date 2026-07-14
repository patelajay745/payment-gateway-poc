package com.example.paymentgateway.payment.config;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.gateway.PaymentAdapter;
import com.example.paymentgateway.payment.gateway.adapter.CardPaymentAdapter;
import com.example.paymentgateway.payment.gateway.adapter.NetBankingAdapter;
import com.example.paymentgateway.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentAdapterConfig {
	
	private final NetBankingAdapter netBankingAdapter;
	
	private final CardPaymentAdapter cardPaymentAdapter;
	
	private final UpiPaymentAdapter upiPaymentAdapter;
	
	@Bean
	public Map<PaymentMethod, PaymentAdapter> paymentAdapters() {
		return Map.of(PaymentMethod.CARD, cardPaymentAdapter,
				PaymentMethod.NETBANKING, netBankingAdapter,
				PaymentMethod.UPI, upiPaymentAdapter
		);
	}
}
