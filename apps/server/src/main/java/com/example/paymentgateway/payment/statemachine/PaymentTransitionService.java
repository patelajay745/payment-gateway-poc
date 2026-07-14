package com.example.paymentgateway.payment.statemachine;

import com.example.paymentgateway.common.enums.PaymentActor;
import com.example.paymentgateway.common.enums.PaymentEvent;
import com.example.paymentgateway.common.enums.PaymentStatus;
import com.example.paymentgateway.payment.entity.Payment;
import com.example.paymentgateway.payment.entity.PaymentTransitionLog;
import com.example.paymentgateway.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentTransitionService {
	
	private final PaymentTransitionLogRepository paymentTransitionLogRepository;
	
	private final PaymentStateMachine paymentStateMachine;
	
	public PaymentStatus apply(Payment payment, PaymentEvent event) {
		PaymentStatus next = paymentStateMachine.transition(payment.getStatus(), event);
		
		payment.setStatus(next);
		
		PaymentTransitionLog paymentTransitionLog = PaymentTransitionLog
				                                            .builder()
				                                            .payment(payment)
				                                            .fromStatus(payment.getStatus())
				                                            .event(event)
				                                            .toStatus(next)
				                                            .actor(PaymentActor.SYSTEM)
				                                            .occurredAt(Instant.now())
				                                            .build();
		
		paymentTransitionLogRepository.save(paymentTransitionLog);
		
		return next;
	}
}
