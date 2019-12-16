package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.event.UserDeletedEvent;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class DeleteUserServiceTest {

    private DeleteUserPort deleteUserPort;

    private DeleteUserService deleteUserService;

    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        deleteUserPort = mock(DeleteUserPort.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        deleteUserService = new DeleteUserService(deleteUserPort, applicationEventPublisher);
    }

    @Test
    void deleteUserIfUserIsRemoved() {
        User.UserId userId = new User.UserId(12345L);
        when(deleteUserPort.deleteUser(userId)).thenReturn(true);

        assertTrue(deleteUserService.deleteUser(new DeleteUserUseCase.DeleteUserCommand(userId)));
        verify(applicationEventPublisher).publishEvent(new UserDeletedEvent(userId));
    }

    @Test
    void deleteUserIfUserIsNotRemoved() {
        User.UserId userId = new User.UserId(12345L);
        when(deleteUserPort.deleteUser(userId)).thenReturn(false);

        assertFalse(deleteUserService.deleteUser(new DeleteUserUseCase.DeleteUserCommand(userId)));
        verifyNoInteractions(applicationEventPublisher);
    }
}
