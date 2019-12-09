package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateUserServiceTest {

    private LoadUsersPort loadUserPort;

    private UpdateUserPort updateUserPort;

    private UpdateUserService updateUserService;

    @BeforeEach
    void setUp() {
        loadUserPort = mock(LoadUsersPort.class);
        updateUserPort = mock(UpdateUserPort.class);
        updateUserService = new UpdateUserService(loadUserPort, updateUserPort);
    }

    @Test
    void updateUser() {
        User.UserId userId = new User.UserId(12345L);
        when(loadUserPort.loadUser(userId)).thenReturn(Optional.of(User.withId(12345L,
                                                                               "Old First name",
                                                                               "Old Last Name",
                                                                               "old@email.com",
                                                                               "pass1",
                                                                               UserRole.USER,
                                                                               true)));
        User updatedUser = User.withId(12345L,
                                       "New First Name",
                                       "New Last Name",
                                       "new@email.com",
                                       "pass1",
                                       UserRole.USER,
                                       true);
        when(updateUserPort.updateUser(updatedUser)).thenReturn(updatedUser);

        UpdateUserUseCase.UpdateUserCommand command = new UpdateUserUseCase.UpdateUserCommand(userId,
                                                                                              "New First Name",
                                                                                              "New Last Name",
                                                                                              "new@email.com");
        User actual = updateUserService.updateUser(command).orElseThrow(AssertionError::new);
        assertEquals(updatedUser, actual);
    }
}
