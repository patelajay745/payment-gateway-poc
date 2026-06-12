package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "app_users")
public class AppUsers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	@Email
	@NotBlank
	private String email;
	
	private String passwordHased;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;
	
	@Column(updatable = false)
	@CreatedDate
	private Instant createdAt;
	
	@Column(updatable = false)
	@UpdateTimestamp
	private Instant updatedAt;
}
