package com.example.paymentgateway.common.security;

import com.example.paymentgateway.merchant.entity.Merchant;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class MerchantPrincipal implements UserDetails {
	
	private final Merchant merchant;
	
	public Merchant getMerchant() {
		return merchant;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
	
	@Override
	public @Nullable String getPassword() {
		return null;
	}
	
	@Override
	public String getUsername() {
		return merchant.getEmail();
	}
}
