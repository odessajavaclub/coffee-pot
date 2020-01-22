package org.odessajavaclub.user.adapter.in.springevents;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserDto;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersResponseEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.SpringEventUserDtoMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserResponseEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
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

  private final UpdateUserUseCase updateUserUseCase;

  @EventListener
  public void createActiveUser(CreateActiveUserRequestEvent requestEvent) {
    User createdUser = createUserUseCase
        .createActiveUser(CreateUserUseCase.CreateUserCommand.builder()
                                                             .firstName(requestEvent.getFirstName())
                                                             .lastName(requestEvent.getLastName())
                                                             .email(requestEvent.getEmail())
                                                             .password(requestEvent.getPassword())
                                                             .role(requestEvent.getRole())
                                                             .build());
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
//    boolean removed = deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(requestEvent.getId()));
    boolean removed = false;
    applicationEventPublisher.publishEvent(new DeleteUserResponseEvent(this, removed));
  }

  @EventListener
  public void updateUser(UpdateUserRequestEvent requestEvent) {
//    updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(requestEvent.getId(),
//                                                                         requestEvent.getNewFirstName(),
//                                                                         requestEvent.getNewLastName(),
//                                                                         requestEvent.getNewEmail()))
    updateUserUseCase.updateUser(null)
                     .map(springEventUserDtoMapper::toGetUserDto)
                     .ifPresentOrElse(
                         u -> applicationEventPublisher.publishEvent(new UpdateUserResponseEvent(this, u)),
                         () -> applicationEventPublisher.publishEvent(new UpdateUserResponseEvent(this, null)));
  }
}
