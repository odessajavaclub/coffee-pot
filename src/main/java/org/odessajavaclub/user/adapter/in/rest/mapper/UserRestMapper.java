package org.odessajavaclub.user.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.odessajavaclub.user.adapter.in.rest.model.CreateUserDto;
import org.odessajavaclub.user.adapter.in.rest.model.GetUserDto;
import org.odessajavaclub.user.adapter.in.rest.model.UpdateUserDto;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase.ActivateUserCommand;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase.CreateUserCommand;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase.DeactivateUserCommand;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase.DeleteUserCommand;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase.UpdateUserCommand;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(componentModel = "spring", uses = {UserIdMapper.class, UserRoleRestMapper.class})
public interface UserRestMapper {

  @Mapping(source = "id", target = "id", defaultValue = "-1L")
  GetUserDto toGetUserDto(User user);

  CreateUserCommand toCreateUserCommand(CreateUserDto createUserDto);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "updateUserDto.firstName", target = "newFirstName")
  @Mapping(source = "updateUserDto.lastName", target = "newLastName")
  @Mapping(source = "updateUserDto.email", target = "newEmail")
  UpdateUserCommand toUpdateUserCommand(Long id, UpdateUserDto updateUserDto);

  DeleteUserCommand toDeleteUserCommand(Long id);

  ActivateUserCommand toActivateUserCommand(Long id);

  DeactivateUserCommand toDeactivateUserCommand(Long id);
}
