package com.example.paymentgateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.paymentgateway.domain.AppUser;
import com.example.paymentgateway.repository.AppUserRepository;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

  @Mock
  private AppUserRepository repository;

  @InjectMocks
  private AppUserService service;

  @Test
  void createPersistsANormalizedUser() {
    when(repository.save(any(AppUser.class))).thenAnswer((invocation) -> invocation.getArgument(0));

    AppUser createdUser = service.create("Ada@example.com ", "Ada Lovelace");

    verify(repository).save(any(AppUser.class));
    assertEquals("ada@example.com", createdUser.getEmail());
    assertEquals("Ada Lovelace", createdUser.getDisplayName());
  }

  @Test
  void findAllDelegatesToRepository() {
    when(repository.findAll()).thenReturn(List.of(new AppUser("grace@example.com", "Grace Hopper")));

    List<AppUser> users = service.findAll();

    assertEquals(1, users.size());
    verify(repository).findAll();
  }
}
