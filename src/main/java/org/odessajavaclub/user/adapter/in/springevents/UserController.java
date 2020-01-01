package org.odessajavaclub.user.adapter.in.springevents;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.SpringEventUserDtoMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersResponseEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class UserController {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final SpringEventUserDtoMapper springEventUserDtoMapper;

    private final CreateUserUseCase createUserUseCase;

    private final GetUsersQuery getUsersQuery;

    private final DeleteUserUseCase deleteUserUseCase;

    @EventListener
    public void createActiveUser(CreateActiveUserRequestEvent requestEvent) {
        User createdUser = createUserUseCase.createActiveUser(new CreateUserUseCase.CreateUserCommand(requestEvent.getFirstName(),
                                                                                                      requestEvent.getLastName(),
                                                                                                      requestEvent.getEmail(),
                                                                                                      requestEvent.getPassword(),
                                                                                                      requestEvent.getRole()));
        applicationEventPublisher.publishEvent(new CreateActiveUserResponseEvent(this,
                                                                                 springEventUserDtoMapper.toGetUserDto(createdUser)));
    }

    @EventListener
    public void getUsers(GetUsersRequestEvent requestEvent) {
        getUsersQuery.getAllUsersByActive(requestEvent.isActive(), requestEvent.getPage(), requestEvent.getSize())
                     .forEach(u -> applicationEventPublisher.publishEvent(new GetUsersResponseEvent(this,
                                                                                                    springEventUserDtoMapper
                                                                                                            .toGetUserDto(u))));
    }

    @EventListener
    public void getUser(GetUserRequestEvent requestEvent) {
        getUsersQuery.getUserById(new User.UserId(requestEvent.getId()))
                     .ifPresent(u -> applicationEventPublisher.publishEvent(new GetUserResponseEvent(this,
                                                                                                     springEventUserDtoMapper
                                                                                                             .toGetUserDto(u))));
    }

    @EventListener
    public void deleteUser(DeleteUserRequestEvent requestEvent) {
        if (deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(requestEvent.getId())))) {
            applicationEventPublisher.publishEvent(new DeleteUserResponseEvent(this, requestEvent.getId()));
        }
    }
}
