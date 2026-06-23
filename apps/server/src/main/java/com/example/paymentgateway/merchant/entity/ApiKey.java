package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.enums.Environment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_keys",
		indexes = {
				@Index(name = "idx_api_key_merchant_id_env", columnList = "merchant_id,environment,enabled")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiKey extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;
	
	@Column(nullable = false, length = 50, unique = true)
	private String keyId;
	
	@Column(nullable = false, length = 200)
	private String keySecretHash;
	
	@Column(length = 200, nullable = true)
	private String prevKeySecretHash;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Environment environment;
	
	@Column(nullable = false)
	@Builder.Default
	private boolean enabled = true;
	
	private Instant lastUsedAt;
	
	private Instant rotatedAt;
	
	private Instant gracePeriodExpiresAt;
	
	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void setPrevKeySecretHash(String keyHash) {
		this.prevKeySecretHash = keyHash;
	}
	
	public void setKeySecretHash(String keyHash) {
		this.keySecretHash = keyHash;
	}
	
	public void setRotatedAt(Instant time) {
		this.rotatedAt = time;
	}
	
	public void setGracePeriodExpiresAt(Instant time) {
		this.gracePeriodExpiresAt = time;
	}
}
