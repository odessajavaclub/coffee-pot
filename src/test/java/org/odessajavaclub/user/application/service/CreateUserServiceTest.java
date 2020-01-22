package org.odessajavaclub.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

class CreateUserServiceTest {

  private CreateUserPort createUserPort;

  private PasswordEncoder passwordEncoder;

  private CreateUserService createUserService;

  @BeforeEach
  void setUp() {
    createUserPort = mock(CreateUserPort.class);
    passwordEncoder = mock(PasswordEncoder.class);
    createUserService = new CreateUserService(createUserPort, passwordEncoder);
  }

  @Test
  void createActiveUserIfInputCommandIsNull() {
    assertThrows(NullPointerException.class,
                 () -> createUserService.createActiveUser(null),
                 "CreateUserCommand must not be null");
  }

  @Test
  void createActiveUserIfInputDataIsValid() {
    User user = User.builder()
                    .firstName("Good")
                    .lastName("User")
                    .email("good@email.com")
                    .password("pass1")
                    .role(UserRole.USER)
                    .active(true)
                    .build();

    when(passwordEncoder.encode("pass1")).thenReturn("pass1");
    when(createUserPort.createUser(user)).thenReturn(user);

    User actual = createUserService.createActiveUser(new CreateUserUseCase.CreateUserCommand("Good",
                                                                                             "User",
                                                                                             "good@email.com",
                                                                                             "pass1",
                                                                                             UserRole.USER));

    assertEquals(user, actual);
  }

  @Test
  void createInactiveUserIfInputCommandIsNull() {
    assertThrows(NullPointerException.class,
                 () -> createUserService.createInactiveUser(null),
                 "CreateUserCommand must not be null");
  }

  @Test
  void createInactiveUserIfInputDataIsValid() {
    User user = User.builder()
                    .firstName("Good")
                    .lastName("User")
                    .email("good@email.com")
                    .password("pass1")
                    .role(UserRole.USER)
                    .active(false)
                    .build();

    when(passwordEncoder.encode("pass1")).thenReturn("pass1");
    when(createUserPort.createUser(user)).thenReturn(user);

    User actual = createUserService.createInactiveUser(new CreateUserUseCase.CreateUserCommand(
        "Good",
        "User",
        "good@email.com",
        "pass1",
        UserRole.USER));

    assertEquals(user, actual);
  }
}
