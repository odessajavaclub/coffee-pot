package org.odessajavaclub.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;

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
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsers()).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsers());
  }

  @Test
  void getAllUsersPaged() {
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsers(123, 456)).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsers(123, 456));
  }

  @Test
  void getActiveUsers() {
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsersByActive(true)).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsersByActive(true));
  }

  @Test
  void getActiveUsersPaged() {
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsersByActive(true, 123, 456)).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsersByActive(true, 123, 456));
  }

  @Test
  void getInactiveUsers() {
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsersByActive(false)).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsersByActive(false));
  }

  @Test
  void getInactiveUsersPaged() {
    List<User> users = List.of(User.builder().build(), User.builder().build());
    when(loadUsersPort.loadAllUsersByActive(false, 123, 456)).thenReturn(users);

    assertEquals(users, getUsersService.getAllUsersByActive(false, 123, 456));
  }

  @Test
  void getUserByIdIfUserIsPresent() {
    User.UserId userId = new User.UserId(12345L);
    User user = User.builder().build();
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
