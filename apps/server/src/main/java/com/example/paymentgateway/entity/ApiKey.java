package com.example.paymentgateway.entity;

import com.example.paymentgateway.common.enums.Environment;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
public class ApiKey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;
	
	@Column(nullable = false, length = 50, unique = true)
	private String keyId;
	
	@Column(nullable = false)
	private String keySecretHash;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Environment environment;
	
	@Column(nullable = false)
	private boolean enabled = true;
	
	private Instant lastUsedAt;
	
	private Instant rotatedAt;
	
	private Instant gracePeriodExpiresAt;
}
