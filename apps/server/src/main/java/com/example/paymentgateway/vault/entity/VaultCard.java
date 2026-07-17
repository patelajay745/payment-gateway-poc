package com.example.paymentgateway.vault.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.enums.CardBrand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vault_card")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaultCard extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 4)
	private String lastFour;
	
	@Column(nullable = false, length = 6)
	private String bin;
	
	@Column(nullable = false)
	private byte[] encryptedPan;
	
	@Column(nullable = false)
	private byte[] encryptedDek;
	
	@Column(nullable = false)
	private CardBrand brand;
	
	@Column(nullable = false)
	private Integer expiryMonth;
	
	@Column(nullable = false)
	private Integer expiryYear;
	
	@Column(nullable = false)
	private String cardHolderName;
	
	private Instant deletedAt;
}

