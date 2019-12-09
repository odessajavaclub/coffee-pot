package org.odessajavaclub.user.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUsersServiceTest {

    private LoadUsersPort loadUsersPort;

    private GetUsersService getUsersService;

    @BeforeEach
    void setUp() {
        loadUsersPort = mock(LoadUsersPort.class);
        getUsersService = new GetUsersService(loadUsersPort);
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(mock(User.class), mock(User.class));
        when(loadUsersPort.loadAllUsers()).thenReturn(users);

        assertEquals(users, getUsersService.getAllUsers());
    }

    @Test
    void getActiveUsers() {
        List<User> users = List.of(mock(User.class), mock(User.class));
        when(loadUsersPort.loadActiveUsers()).thenReturn(users);

        assertEquals(users, getUsersService.getActiveUsers());
    }

    @Test
    void getInactiveUsers() {
        List<User> users = List.of(mock(User.class), mock(User.class));
        when(loadUsersPort.loadInactiveUsers()).thenReturn(users);

        assertEquals(users, getUsersService.getInactiveUsers());
    }

    @Test
    void getUserByIdIfUserIsPresent() {
        User.UserId userId = new User.UserId(12345L);
        User user = mock(User.class);
        when(loadUsersPort.loadUser(userId)).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), getUsersService.getUserById(userId));
    }

    @Test
    void getUserByIdIfUserIsAbsent() {
        User.UserId userId = new User.UserId(12345L);
        when(loadUsersPort.loadUser(userId)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), getUsersService.getUserById(userId));
    }
}
