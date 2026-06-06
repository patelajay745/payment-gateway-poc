package com.example.paymentgateway.controller;

import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentgateway.domain.AppUser;
import com.example.paymentgateway.service.AppUserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final AppUserService userService;

  public UserController(AppUserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserResponse> listUsers() {
    return userService.findAll().stream().map(UserResponse::from).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

    AppUser createdUser = userService.create(request.email(), request.displayName());
    return UserResponse.from(createdUser);
  }

  public record CreateUserRequest(@NotBlank @Email String email, @NotBlank String displayName) {
  }

  public record UserResponse(Long id, String email, String displayName, Instant createdAt) {
    static UserResponse from(AppUser user) {
      return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getDisplayName(),
        user.getCreatedAt()
      );
    }
  }
}
