package org.odessajavaclub.user.adapter.in.springevents;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.adapter.in.springevents.mapper.UserSpringEventMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserDto;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserResponseEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("springevents")
@Component
@RequiredArgsConstructor
public class UserController {

  private final ApplicationEventPublisher applicationEventPublisher;

  private final CreateUserUseCase createUserUseCase;

  private final GetUsersQuery getUsersQuery;

  private final DeleteUserUseCase deleteUserUseCase;

  private final UpdateUserUseCase updateUserUseCase;

  private final UserSpringEventMapper springEventUserDtoMapper;

  @EventListener
  public void createActiveUser(CreateActiveUserRequestEvent requestEvent) {
    User createdUser = createUserUseCase.createActiveUser(springEventUserDtoMapper.toCreateUserCommand(requestEvent));
    applicationEventPublisher.publishEvent(new CreateActiveUserResponseEvent(this,
                                                                             springEventUserDtoMapper.toGetUserDto(createdUser)));
  }

  @EventListener
  public void getUsers(GetUsersRequestEvent requestEvent) {
    List<GetUserDto> users = getUsersQuery.getAllUsersByActive(requestEvent.isActive(),
                                                               requestEvent.getPage(),
                                                               requestEvent.getSize())
                                          .stream()
                                          .map(springEventUserDtoMapper::toGetUserDto)
                                          .collect(Collectors.toList());
    applicationEventPublisher.publishEvent(new GetUsersResponseEvent(this, users));
  }

  @EventListener
  public void getUser(GetUserRequestEvent requestEvent) {
    getUsersQuery.getUserById(requestEvent.getId())
                 .ifPresent(u -> applicationEventPublisher.publishEvent(new GetUserResponseEvent(this,
                                                                                                 springEventUserDtoMapper.toGetUserDto(u))));
  }

  @EventListener
  public void deleteUser(DeleteUserRequestEvent requestEvent) {
    boolean removed = deleteUserUseCase.deleteUser(springEventUserDtoMapper.toDeleteUserCommand(requestEvent));
    applicationEventPublisher.publishEvent(new DeleteUserResponseEvent(this, removed));
  }

  @EventListener
  public void updateUser(UpdateUserRequestEvent requestEvent) {
    updateUserUseCase.updateUser(springEventUserDtoMapper.toUpdateUserCommand(requestEvent))
                     .map(springEventUserDtoMapper::toGetUserDto)
                     .ifPresentOrElse(
                         u -> applicationEventPublisher.publishEvent(new UpdateUserResponseEvent(this, u)),
                         () -> applicationEventPublisher.publishEvent(new UpdateUserResponseEvent(this, null)));
  }
}
