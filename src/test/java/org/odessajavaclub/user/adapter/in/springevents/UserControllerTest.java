package org.odessajavaclub.user.adapter.in.springevents;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.odessajavaclub.user.adapter.in.springevents.UserControllerTest.TestConfig;
import org.odessajavaclub.user.adapter.in.springevents.mapper.UserSpringEventMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.UserSpringEventRole;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;
import org.odessajavaclub.user.shared.UserIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("springevents")
@SpringBootTest(classes = UserController.class)
@Import(TestConfig.class)
class UserControllerTest {

  @TestConfiguration
  static class TestConfig {

    @Bean
    UserSpringEventMapper userSpringEventMapper() {
      return Mappers.getMapper(UserSpringEventMapper.class);
    }

    @Bean
    UserIdMapper userIdMapper() {
      return new UserIdMapper();
    }
  }

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @MockBean
  private CreateUserUseCase createUserUseCase;

  @MockBean
  private GetUsersQuery getUsersQuery;

  @MockBean
  private DeleteUserUseCase deleteUserUseCase;

  @MockBean
  private UpdateUserUseCase updateUserUseCase;

  @Test
  void createActiveUser() {
    User user1 = User.builder()
                     .id(new UserId(1L))
                     .firstName("Maxim")
                     .lastName("Sashkin")
                     .email("maxs@email.com")
                     .password("{noop}maxs1234")
                     .role(UserRole.USER)
                     .active(true)
                     .build();

    when(createUserUseCase.createActiveUser(any(CreateUserUseCase.CreateUserCommand.class)))
        .thenReturn(user1);

    applicationEventPublisher.publishEvent(new CreateActiveUserRequestEvent("User",
                                                                            "One",
                                                                            "userone@email.com",
                                                                            "pass1",
                                                                            UserSpringEventRole.USER));

    verify(createUserUseCase).createActiveUser(any(CreateUserUseCase.CreateUserCommand.class));
  }

  @Test
  void getUsers() {
    User user1 = User.builder()
                     .id(new UserId(1L))
                     .firstName("Maxim")
                     .lastName("Sashkin")
                     .email("maxs@email.com")
                     .password("{noop}maxs1234")
                     .role(UserRole.USER)
                     .active(true)
                     .build();
    User user2 = User.builder()
                     .id(new UserId(2L))
                     .firstName("Alexander")
                     .lastName("Pletnev")
                     .email("alexp@email.com")
                     .password("{noop}alexp1234")
                     .role(UserRole.ADMIN)
                     .active(true)
                     .build();

    when(getUsersQuery.getAllUsersByActive(true, 6, 666))
        .thenReturn(List.of(user1, user2));

    applicationEventPublisher.publishEvent(new GetUsersRequestEvent(true, 6, 666));

    verify(getUsersQuery).getAllUsersByActive(true, 6, 666);
  }

  @Test
  void getUserIfPresent() {
    User user1 = User.builder()
                     .id(new UserId(1L))
                     .firstName("Maxim")
                     .lastName("Sashkin")
                     .email("maxs@email.com")
                     .password("{noop}maxs1234")
                     .role(UserRole.USER)
                     .active(true)
                     .build();

    when(getUsersQuery.getUserById(new User.UserId(777L)))
        .thenReturn(Optional.of(user1));

    applicationEventPublisher.publishEvent(new GetUserRequestEvent(new User.UserId(777L)));

    verify(getUsersQuery).getUserById(new User.UserId(777L));
  }

  @Test
  void getUserIfAbsent() {
    when(getUsersQuery.getUserById(new User.UserId(777L))).thenReturn(Optional.empty());

    applicationEventPublisher.publishEvent(new GetUserRequestEvent(new User.UserId(777L)));

    verify(getUsersQuery).getUserById(new User.UserId(777L));
  }

  @Test
  void deleteUserIfPresent() {
    when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L))))
        .thenReturn(true);

    applicationEventPublisher.publishEvent(new DeleteUserRequestEvent(new User.UserId(123L)));

    verify(deleteUserUseCase).deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L)));
  }

  @Test
  void deleteUserIfAbsent() {
    when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L))))
        .thenReturn(false);

    applicationEventPublisher.publishEvent(new DeleteUserRequestEvent(new User.UserId(123L)));

    verify(deleteUserUseCase).deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L)));
  }

  @Test
  void updateUserIfPresent() {
    when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(123L),
                                                                              "New",
                                                                              "User",
                                                                              "newemail@email.com")))
        .thenReturn(Optional.of(User.builder()
                                    .id(new UserId(123L))
                                    .firstName("New")
                                    .lastName("User")
                                    .email("newemail@email.com")
                                    .password("pass123")
                                    .role(UserRole.ADMIN)
                                    .active(true)
                                    .build()));

    applicationEventPublisher.publishEvent(new UpdateUserRequestEvent(new User.UserId(123L),
                                                                      "New",
                                                                      "User",
                                                                      "newemail@email.com"));

    verify(updateUserUseCase).updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(123L),
                                                                                 "New",
                                                                                 "User",
                                                                                 "newemail@email.com"));
  }

  @Test
  void updateUserIfAbsent() {
    when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(123L),
                                                                              "New",
                                                                              "User",
                                                                              "newemail@email.com")))
        .thenReturn(Optional.empty());

    applicationEventPublisher.publishEvent(new UpdateUserRequestEvent(new User.UserId(123L),
                                                                      "New",
                                                                      "User",
                                                                      "newemail@email.com"));

    verify(updateUserUseCase).updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(123L),
                                                                                 "New",
                                                                                 "User",
                                                                                 "newemail@email.com"));
  }
}
