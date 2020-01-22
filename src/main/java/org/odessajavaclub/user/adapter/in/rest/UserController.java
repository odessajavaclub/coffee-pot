package org.odessajavaclub.user.adapter.in.rest;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase.CreateUserCommand;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;

  private final GetUsersQuery getUsersQuery;

  private final DeleteUserUseCase deleteUserUseCase;

  private final UpdateUserUseCase updateUserUseCase;

  private final ActivateUserUseCase activateUserUseCase;

  private final DeactivateUserUseCase deactivateUserUseCase;

  private final UserMapper userMapper;

  @PostMapping
  GetUserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
    CreateUserCommand command = userMapper.createUserDtoToCreateUserCommand(createUserDto);
    User user = createUserUseCase.createActiveUser(command);
    return userMapper.userToGetUserDto(user);
  }

  @GetMapping
  List<GetUserDto> getUsers(@RequestParam(required = false) Boolean active,
                            @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "100") int size) {
    if (active == null) {
      return getUsersQuery.getAllUsers(page, size)
                          .stream()
                          .map(userMapper::userToGetUserDto)
                          .collect(Collectors.toList());
    } else {
      return getUsersQuery.getAllUsersByActive(active, page, size)
                          .stream()
                          .map(userMapper::userToGetUserDto)
                          .collect(Collectors.toList());
    }
  }

  @GetMapping("/{id}")
  ResponseEntity<GetUserDto> getUser(@PathVariable Long id) {
    return getUsersQuery.getUserById(new User.UserId(id))
                        .map(userMapper::userToGetUserDto)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteUser(@PathVariable Long id) {
    return
        deleteUserUseCase.deleteUser(DeleteUserUseCase.DeleteUserCommand.builder()
                                                                        .userId(new User.UserId(id))
                                                                        .build())
        ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  ResponseEntity<GetUserDto> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UpdateUserDto user) {
    return updateUserUseCase.updateUser(userMapper.updateUserDtoToUpdateUserCommand(id, user))
                            .map(userMapper::userToGetUserDto)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/activate/{id}")
  ResponseEntity<?> activateUser(@PathVariable Long id) {
    return activateUserUseCase
        .activateUser(ActivateUserUseCase.ActivateUserCommand.builder()
                                                             .userId(new User.UserId(id))
                                                             .build())
        .map(userMapper::userToGetUserDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/deactivate/{id}")
  ResponseEntity<?> deactivateUser(@PathVariable Long id) {
    return deactivateUserUseCase
        .deactivateUser(DeactivateUserUseCase.DeactivateUserCommand.builder()
                                                                   .userId(new User.UserId(id))
                                                                   .build())
        .map(userMapper::userToGetUserDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
