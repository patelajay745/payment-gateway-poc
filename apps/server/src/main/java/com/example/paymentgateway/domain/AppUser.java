package com.example.paymentgateway.domain;

import java.time.Instant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @Email
  @NotBlank
  private String email;

  @Column(nullable = false)
  @NotBlank
  private String displayName;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  public AppUser() {
  }

  public AppUser(String email, String displayName) {
    this.email = email;
    this.displayName = displayName;
  }

  @PrePersist
  void onCreate() {
    if (createdAt == null) {
      createdAt = Instant.now();
    }
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
