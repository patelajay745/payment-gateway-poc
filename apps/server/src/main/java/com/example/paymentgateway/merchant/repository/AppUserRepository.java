package com.example.paymentgateway.merchant.repository;

import com.example.paymentgateway.merchant.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUsers, UUID> {
	
	Optional<AppUsers> findByEmail(String email);
}
