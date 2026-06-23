package com.example.paymentgateway.merchant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchant_webhook_config",
		indexes = {
				@Index(name = "idx_webhook_merchant_id", columnList = "merchant_id")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantWebhookConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;
	
	@Column(length = 200)
	private String targetUrl;
	
	private String eventTypeFilter;
	
	@Column(nullable = false)
	private Boolean enabled = true;
	
	@Column(length = 200, nullable = false)
	private String webhook_secret;
	
	private Instant createdAt;
}
