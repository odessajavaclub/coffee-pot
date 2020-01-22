package org.odessajavaclub.user.adapter.in.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase.CreateUserCommand;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase.UpdateUserCommand;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(uses = UserIdMapper.class)
public interface UserMapper {

  @Mapping(source = "id", target = "id", defaultValue = "-1L")
  GetUserDto userToGetUserDto(User user);

  CreateUserCommand createUserDtoToCreateUserCommand(CreateUserDto createUserDto);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "updateUserDto.firstName", target = "newFirstName")
  @Mapping(source = "updateUserDto.lastName", target = "newLastName")
  @Mapping(source = "updateUserDto.email", target = "newEmail")
  UpdateUserCommand updateUserDtoToUpdateUserCommand(Long id, UpdateUserDto updateUserDto);
}
