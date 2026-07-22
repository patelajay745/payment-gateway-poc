package com.example.paymentgateway.merchant.entity;

import com.example.paymentgateway.common.entity.BaseEntity;
import com.example.paymentgateway.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_users",
		indexes = {
				@Index(name = "idx_app_users_merchant_id", columnList = "merchant_id")
		})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUsers extends BaseEntity implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String passwordHashed;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
				new SimpleGrantedAuthority("ROLE_" + role)
		);
	}
	
	@Override
	public @Nullable String getPassword() {
		return passwordHashed;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
}
