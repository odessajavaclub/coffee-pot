package org.odessajavaclub.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;

class ActivationUserServiceTest {

  private LoadUsersPort loadUsersPort;

  private UpdateUserPort updateUserPort;

  private ActivationUserService activationUserService;

  private Validating validating;

  @BeforeEach
  void setUp() {
    loadUsersPort = mock(LoadUsersPort.class);
    updateUserPort = mock(UpdateUserPort.class);
    validating = mock(Validating.class);
    activationUserService = new ActivationUserService(loadUsersPort, updateUserPort, validating);
  }

  @Test
  void activateUserIfUserIsPresent() {
    User user = User.builder()
                    .id(new UserId(1L))
                    .firstName("First name 1")
                    .lastName("Last name 1")
                    .email("one@email.com")
                    .password("pass1")
                    .role(UserRole.ADMIN)
                    .active(false)
                    .build();
    User activatedUser = user.toBuilder().active(true).build();
    when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.of(user));
    when(updateUserPort.updateUser(activatedUser)).thenReturn(activatedUser);

    ActivateUserUseCase.ActivateUserCommand command = new ActivateUserUseCase.ActivateUserCommand(
        new User.UserId(123L));
    Optional<User> actual = activationUserService.activateUser(command);

    assertEquals(activatedUser, actual.orElse(null));
  }

  @Test
  void activateUserIfUserIsAbsent() {
    when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.empty());

    ActivateUserUseCase.ActivateUserCommand command = new ActivateUserUseCase.ActivateUserCommand(
        new User.UserId(123L));
    Optional<User> actual = activationUserService.activateUser(command);

    assertEquals(Optional.empty(), actual);
  }

  @Test
  void deactivateUserIfUserIsPresent() {
    User user = User.builder()
                    .id(new UserId(1L))
                    .firstName("First name 1")
                    .lastName("Last name 1")
                    .email("one@email.com")
                    .password("pass1")
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build();
    User deactivatedUser = user.toBuilder().active(false).build();
    when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.of(user));
    when(updateUserPort.updateUser(deactivatedUser)).thenReturn(deactivatedUser);

    DeactivateUserUseCase.DeactivateUserCommand command = new DeactivateUserUseCase.DeactivateUserCommand(
        new User.UserId(123L));
    Optional<User> actual = activationUserService.deactivateUser(command);

    assertEquals(deactivatedUser, actual.orElse(null));
  }

  @Test
  void deactivateUserIfUserIsAbsent() {
    when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.empty());

    DeactivateUserUseCase.DeactivateUserCommand command = new DeactivateUserUseCase.DeactivateUserCommand(
        new User.UserId(123L));
    Optional<User> actual = activationUserService.deactivateUser(command);

    assertEquals(Optional.empty(), actual);
  }
}
