package org.odessajavaclub.user.adapter.in.springevents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.SpringEventUserDtoMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserController.class)
class UserControllerTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private SpringEventUserDtoMapper springEventUserDtoMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private GetUsersQuery getUsersQuery;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setUp() {
        new UserController(applicationEventPublisher,
                           springEventUserDtoMapper,
                           createUserUseCase,
                           getUsersQuery,
                           deleteUserUseCase);
    }

    @Test
    void createActiveUser() {
        User user1 = mock(User.class);
        when(createUserUseCase.createActiveUser(any(CreateUserUseCase.CreateUserCommand.class))).thenReturn(user1);

        applicationEventPublisher.publishEvent(new CreateActiveUserRequestEvent(this,
                                                                                "User",
                                                                                "One",
                                                                                "userone@email.com",
                                                                                "pass1",
                                                                                UserRole.USER));

        verify(createUserUseCase).createActiveUser(any(CreateUserUseCase.CreateUserCommand.class));
        verify(springEventUserDtoMapper).toGetUserDto(user1);
    }

    @Test
    void getUsers() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        when(getUsersQuery.getAllUsersByActive(true, 6, 666)).thenReturn(List.of(user1, user2));

        applicationEventPublisher.publishEvent(new GetUsersRequestEvent(this, true, 6, 666));

        verify(getUsersQuery).getAllUsersByActive(true, 6, 666);
        verify(springEventUserDtoMapper).toGetUserDto(user1);
        verify(springEventUserDtoMapper).toGetUserDto(user2);
    }

    @Test
    void getUserIfPresent() {
        User user1 = mock(User.class);
        when(getUsersQuery.getUserById(new User.UserId(777L))).thenReturn(Optional.of(user1));

        applicationEventPublisher.publishEvent(new GetUserRequestEvent(this, 777L));

        verify(getUsersQuery).getUserById(new User.UserId(777L));
        verify(springEventUserDtoMapper).toGetUserDto(user1);
    }

    @Test
    void getUserIfAbsent() {
        when(getUsersQuery.getUserById(new User.UserId(777L))).thenReturn(Optional.empty());

        applicationEventPublisher.publishEvent(new GetUserRequestEvent(this, 777L));

        verify(getUsersQuery).getUserById(new User.UserId(777L));
        verifyNoInteractions(springEventUserDtoMapper);
    }

    @Test
    void deleteUserIfPresent() {
        when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L))))
                .thenReturn(true);

        applicationEventPublisher.publishEvent(new DeleteUserRequestEvent(this, 123L));

        verify(deleteUserUseCase).deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L)));
    }

    @Test
    void deleteUserIfAbsent() {
        when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L))))
                .thenReturn(false);

        applicationEventPublisher.publishEvent(new DeleteUserRequestEvent(this, 123L));

        verify(deleteUserUseCase).deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(123L)));
    }
}
