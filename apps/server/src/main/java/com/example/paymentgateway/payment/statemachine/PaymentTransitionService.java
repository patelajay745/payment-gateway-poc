package com.example.paymentgateway.payment.statemachine;

import com.example.paymentgateway.common.enums.PaymentActor;
import com.example.paymentgateway.common.enums.PaymentEvent;
import com.example.paymentgateway.common.enums.PaymentStatus;
import com.example.paymentgateway.payment.entity.Payment;
import com.example.paymentgateway.payment.entity.PaymentTransitionLog;
import com.example.paymentgateway.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentTransitionService {
	
	private final PaymentTransitionLogRepository paymentTransitionLogRepository;
	
	private final PaymentStateMachine paymentStateMachine;
	
	public PaymentStatus apply(Payment payment, PaymentEvent event, String reason) {
		PaymentStatus from = payment.getStatus();
		PaymentStatus next = paymentStateMachine.transition(from, event);

		payment.setStatus(next);

		log.info("Payment transition [{}] : {} --[{}]--> {} | reason: {}", payment.getId(), from, event, next, reason);

		PaymentTransitionLog paymentTransitionLog = PaymentTransitionLog
				                                            .builder()
				                                            .payment(payment)
				                                            .fromStatus(from)
				                                            .event(event)
				                                            .toStatus(next)
				                                            .actor(PaymentActor.SYSTEM)
				                                            .reason(reason)
				                                            .occurredAt(Instant.now())
				                                            .build();

		paymentTransitionLogRepository.save(paymentTransitionLog);

		return next;
	}
}
