package org.odessajavaclub.user.application.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.domain.User;

class DeleteUserServiceTest {

  private DeleteUserPort deleteUserPort;

  private DeleteUserService deleteUserService;

  private Validating validating;

  @BeforeEach
  void setUp() {
    deleteUserPort = mock(DeleteUserPort.class);
    validating = mock(Validating.class);
    deleteUserService = new DeleteUserService(deleteUserPort, validating);
  }

  @Test
  void deleteUserIfUserIsRemoved() {
    User.UserId userId = new User.UserId(12345L);
    when(deleteUserPort.deleteUser(userId)).thenReturn(true);

    assertTrue(deleteUserService.deleteUser(new DeleteUserUseCase.DeleteUserCommand(userId)));
  }

  @Test
  void deleteUserIfUserIsNotRemoved() {
    User.UserId userId = new User.UserId(12345L);
    when(deleteUserPort.deleteUser(userId)).thenReturn(false);

    assertFalse(deleteUserService.deleteUser(new DeleteUserUseCase.DeleteUserCommand(userId)));
  }
}
