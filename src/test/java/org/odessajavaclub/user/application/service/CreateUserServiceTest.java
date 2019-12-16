package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.event.UserCreatedEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CreateUserServiceTest {

    private CreateUserPort createUserPort;

    private PasswordEncoder passwordEncoder;

    private CreateUserService createUserService;

    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        createUserPort = mock(CreateUserPort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        createUserService = new CreateUserService(createUserPort, passwordEncoder, applicationEventPublisher);
    }

    @Test
    void createActiveUserIfInputCommandIsNull() {
        assertThrows(NullPointerException.class,
                     () -> createUserService.createActiveUser(null),
                     "CreateUserCommand must not be null");
        verifyNoInteractions(applicationEventPublisher);
    }

    @Test
    void createActiveUserIfInputDataIsValid() {
        User user = User.withoutId("Good",
                                   "User",
                                   "good@email.com",
                                   "pass1",
                                   UserRole.USER,
                                   true);

        when(passwordEncoder.encode("pass1")).thenReturn("pass1");
        when(createUserPort.createUser(user)).thenReturn(user);

        User actual = createUserService.createActiveUser(new CreateUserUseCase.CreateUserCommand("Good",
                                                                                                 "User",
                                                                                                 "good@email.com",
                                                                                                 "pass1",
                                                                                                 UserRole.USER));

        assertEquals(user, actual);
        verify(applicationEventPublisher).publishEvent(new UserCreatedEvent(user));
    }

    @Test
    void createInactiveUserIfInputCommandIsNull() {
        assertThrows(NullPointerException.class,
                     () -> createUserService.createInactiveUser(null),
                     "CreateUserCommand must not be null");
        verifyNoInteractions(applicationEventPublisher);
    }

    @Test
    void createInactiveUserIfInputDataIsValid() {
        User user = User.withoutId("Good",
                                   "User",
                                   "good@email.com",
                                   "pass1",
                                   UserRole.USER,
                                   false);

        when(passwordEncoder.encode("pass1")).thenReturn("pass1");
        when(createUserPort.createUser(user)).thenReturn(user);

        User actual = createUserService.createInactiveUser(new CreateUserUseCase.CreateUserCommand("Good",
                                                                                                   "User",
                                                                                                   "good@email.com",
                                                                                                   "pass1",
                                                                                                   UserRole.USER));

        assertEquals(user, actual);
        verify(applicationEventPublisher).publishEvent(new UserCreatedEvent(user));
    }
}
