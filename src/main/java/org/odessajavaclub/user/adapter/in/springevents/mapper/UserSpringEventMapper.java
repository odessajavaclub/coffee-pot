package org.odessajavaclub.user.adapter.in.springevents.mapper;

import org.mapstruct.Mapper;
import org.odessajavaclub.user.adapter.in.springevents.model.CreateActiveUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.DeleteUserRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserDto;
import org.odessajavaclub.user.adapter.in.springevents.model.UpdateUserRequestEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase.CreateUserCommand;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase.DeleteUserCommand;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase.UpdateUserCommand;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(componentModel = "spring", uses = {UserIdMapper.class})
public interface UserSpringEventMapper {

  GetUserDto toGetUserDto(User user);

  CreateUserCommand toCreateUserCommand(CreateActiveUserRequestEvent requestEvent);

  DeleteUserCommand toDeleteUserCommand(DeleteUserRequestEvent requestEvent);

  UpdateUserCommand toUpdateUserCommand(UpdateUserRequestEvent requestEvent);
}
