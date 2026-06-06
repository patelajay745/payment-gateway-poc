package com.example.paymentgateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentgateway.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByEmail(String email);
}
