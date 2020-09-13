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
import org.odessajavaclub.user.application.port.in.CreateUserUseCase.CreateUserCommand;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase.DeleteUserCommand;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Profile("springevents")
@Component
@RequiredArgsConstructor
public class UserController {

  private final ApplicationEventPublisher publisher;

  private final CreateUserUseCase createUserUseCase;

  private final GetUsersQuery getUsersQuery;

  private final DeleteUserUseCase deleteUserUseCase;

  private final UpdateUserUseCase updateUserUseCase;

  private final UserSpringEventMapper springEventUserDtoMapper;

  /**
   * By default spring events are synchronous, meaning the publisher thread blocks until
   * all listeners have finished processing the event.
   *
   * <p>To make an event listener run in async mode,
   * all we have to do is use the @Async annotation on that listener.
   * To make the @Async annotation work, we also have to annotate one of our @Configuration classes
   * or the @SpringBootApplication class with @EnableAsync.
   *
   * @return {@link CreateActiveUserResponseEvent}
   */
  @Async
  @EventListener
  CreateActiveUserResponseEvent createActiveUser(CreateActiveUserRequestEvent requestEvent) {
    CreateUserCommand command = springEventUserDtoMapper.toCreateUserCommand(requestEvent);
    User createdUser = createUserUseCase.createActiveUser(command);
    GetUserDto getUserDto = springEventUserDtoMapper.toGetUserDto(createdUser);

    // For the methods annotated with @EventListener and defined as a non-void return type,
    // Spring will publish the result as a new event for us.
    return new CreateActiveUserResponseEvent(getUserDto);
  }

  @Async
  @EventListener
  GetUsersResponseEvent getUsers(GetUsersRequestEvent requestEvent) {
    List<GetUserDto> users = getUsersQuery.getAllUsersByActive(requestEvent.isActive(),
                                                               requestEvent.getPage(),
                                                               requestEvent.getSize())
                                          .stream()
                                          .map(springEventUserDtoMapper::toGetUserDto)
                                          .collect(Collectors.toList());
    return new GetUsersResponseEvent(users);
  }

  @Async
  @EventListener
  void getUser(GetUserRequestEvent requestEvent) {
    getUsersQuery.getUserById(requestEvent.getId())
                 .map(u -> new GetUserResponseEvent(springEventUserDtoMapper.toGetUserDto(u)))
                 .ifPresent(publisher::publishEvent);
  }

  @Async
  @EventListener
  DeleteUserResponseEvent deleteUser(DeleteUserRequestEvent requestEvent) {
    DeleteUserCommand command = springEventUserDtoMapper.toDeleteUserCommand(requestEvent);
    boolean removed = deleteUserUseCase.deleteUser(command);
    return new DeleteUserResponseEvent(removed);
  }

  @Async
  @EventListener
  void updateUser(UpdateUserRequestEvent requestEvent) {
    updateUserUseCase.updateUser(springEventUserDtoMapper.toUpdateUserCommand(requestEvent))
                     .map(springEventUserDtoMapper::toGetUserDto)
                     .ifPresentOrElse(
                         u -> publisher.publishEvent(new UpdateUserResponseEvent(u)),
                         () -> publisher.publishEvent(new UpdateUserResponseEvent(null)));
  }
}
