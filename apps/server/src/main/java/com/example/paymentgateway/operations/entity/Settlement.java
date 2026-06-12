package com.example.paymentgateway.operations.entity;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.SettlementStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "card_token")
public class Settlement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private UUID merchantId;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amountUnits", column = @Column(name = "gross_amount_units", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "gross_currency_units", nullable = false))
	})
	private Money grossAmount;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amountUnits", column = @Column(name = "refund_amount_units", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "refund_currency_units", nullable = false))
	})
	private Money refundAmount;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amountUnits", column = @Column(name = "gst_amount_units", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "gst_currency_units", nullable = false))
	})
	private Money gstAmount;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amountUnits", column = @Column(name = "fee_amount_units", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "fee_currency_units", nullable = false))
	})
	private Money feeAmount;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amountUnits", column = @Column(name = "net_amount_units", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "net_currency_units", nullable = false))
	})
	private Money netAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private SettlementStatus status;
	
	@Column(nullable = false, length = 50)
	private String bankReference;
	
	private Instant processedAt;
}
