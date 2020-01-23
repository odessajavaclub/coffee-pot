package org.odessajavaclub.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;

class UpdateUserServiceTest {

  private LoadUsersPort loadUserPort;

  private UpdateUserPort updateUserPort;

  private UpdateUserService updateUserService;

  private Validating validating;

  @BeforeEach
  void setUp() {
    loadUserPort = mock(LoadUsersPort.class);
    updateUserPort = mock(UpdateUserPort.class);
    validating = mock(Validating.class);
    updateUserService = new UpdateUserService(loadUserPort, updateUserPort, validating);
  }

  @Test
  void updateUser() {
    User.UserId userId = new User.UserId(12345L);

    User oldUser = User.builder()
                       .id(new UserId(12345L))
                       .firstName("Old First Name")
                       .lastName("Old Last Name")
                       .email("old@email.com")
                       .password("pass1")
                       .role(UserRole.USER)
                       .active(true)
                       .build();
    when(loadUserPort.loadUser(userId)).thenReturn(Optional.of(oldUser));
    User newUser = User.builder()
                       .id(new UserId(12345L))
                       .firstName("New First Name")
                       .lastName("New Last Name")
                       .email("new@email.com")
                       .password("pass1")
                       .role(UserRole.USER)
                       .active(true)
                       .build();
    when(updateUserPort.updateUser(newUser)).thenReturn(newUser);

    UpdateUserUseCase.UpdateUserCommand command = new UpdateUserUseCase.UpdateUserCommand(userId,
                                                                                          "New First Name",
                                                                                          "New Last Name",
                                                                                          "new@email.com");
    User actual = updateUserService.updateUser(command).orElseThrow(AssertionError::new);
    assertEquals(newUser, actual);
  }
}
