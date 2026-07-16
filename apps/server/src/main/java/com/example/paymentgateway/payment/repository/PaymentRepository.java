package com.example.paymentgateway.payment.repository;

import com.example.paymentgateway.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
	
	List<Payment> findByOrderId(UUID id);
	
	Optional<Payment> findByIdAndMerchantId(UUID paymentId, UUID id);
}
