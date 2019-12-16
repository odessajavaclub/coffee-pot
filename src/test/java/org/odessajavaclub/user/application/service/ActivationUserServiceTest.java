package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.event.UserActivatedEvent;
import org.odessajavaclub.user.application.event.UserDeactivatedEvent;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class ActivationUserServiceTest {

    private LoadUsersPort loadUsersPort;

    private UpdateUserPort updateUserPort;

    private ActivationUserService activationUserService;

    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        loadUsersPort = mock(LoadUsersPort.class);
        updateUserPort = mock(UpdateUserPort.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        activationUserService = new ActivationUserService(loadUsersPort, updateUserPort, applicationEventPublisher);
    }

    @Test
    void activateUserIfUserIsPresent() {
        User user = User.withId(1L,
                                "First name 1",
                                "Last name 1",
                                "one@email.com",
                                "pass1",
                                UserRole.ADMIN,
                                false);
        User activatedUser = User.from(user, true);
        when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.of(user));
        when(updateUserPort.updateUser(activatedUser)).thenReturn(activatedUser);

        ActivateUserUseCase.ActivateUserCommand command = new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L));
        Optional<User> actual = activationUserService.activateUser(command);

        assertEquals(activatedUser, actual.orElse(null));
        verify(applicationEventPublisher).publishEvent(new UserActivatedEvent(activatedUser));
    }

    @Test
    void activateUserIfUserIsAbsent() {
        when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.empty());

        ActivateUserUseCase.ActivateUserCommand command = new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L));
        Optional<User> actual = activationUserService.activateUser(command);

        assertEquals(Optional.empty(), actual);
        verifyNoInteractions(applicationEventPublisher);
    }

    @Test
    void deactivateUserIfUserIsPresent() {
        User user = User.withId(1L,
                                "First name 1",
                                "Last name 1",
                                "one@email.com",
                                "pass1",
                                UserRole.ADMIN,
                                true);
        User deactivatedUser = User.from(user, false);
        when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.of(user));
        when(updateUserPort.updateUser(deactivatedUser)).thenReturn(deactivatedUser);

        DeactivateUserUseCase.DeactivateUserCommand command = new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(123L));
        Optional<User> actual = activationUserService.deactivateUser(command);

        assertEquals(deactivatedUser, actual.orElse(null));
        verify(applicationEventPublisher).publishEvent(new UserDeactivatedEvent(deactivatedUser));
    }

    @Test
    void deactivateUserIfUserIsAbsent() {
        when(loadUsersPort.loadUser(new User.UserId(123L))).thenReturn(Optional.empty());

        DeactivateUserUseCase.DeactivateUserCommand command = new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(123L));
        Optional<User> actual = activationUserService.deactivateUser(command);

        assertEquals(Optional.empty(), actual);
        verifyNoInteractions(applicationEventPublisher);
    }
}
