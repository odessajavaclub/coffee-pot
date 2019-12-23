package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.domain.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteUserServiceTest {

    private DeleteUserPort deleteUserPort;

    private DeleteUserService deleteUserService;

    @BeforeEach
    void setUp() {
        deleteUserPort = mock(DeleteUserPort.class);
        deleteUserService = new DeleteUserService(deleteUserPort);
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
