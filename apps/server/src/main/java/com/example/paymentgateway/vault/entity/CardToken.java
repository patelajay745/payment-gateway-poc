package com.example.paymentgateway.vault.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "card_token")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardToken extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 50, unique = true)
	private String token;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vault_card_id", nullable = false)
	private VaultCard vaultCard;
	
	@Column(nullable = false)
	private UUID customer;
	
	@Column(nullable = false)
	private UUID merchant;
	
	private Instant revokedAt;
}
