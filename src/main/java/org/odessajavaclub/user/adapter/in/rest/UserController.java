package org.odessajavaclub.user.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    private final UserDtoMapper userDtoMapper;

    @PostMapping
    GetUserDto createUser(@Valid @RequestBody CreateUserDto user) {
        CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(user.getFirstName(),
                                                                                              user.getLastName(),
                                                                                              user.getEmail(),
                                                                                              user.getPassword(),
                                                                                              userDtoMapper.toUserRole(
                                                                                                      user.getRole()));
        return userDtoMapper.toGetUserDto(createUserUseCase.createActiveUser(command));
    }

    @GetMapping
    List<GetUserDto> getUsers(@RequestParam(required = false) Boolean active,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size
    ) {
        if (active == null) {
            if (page != null && size != null) {
                return getUsersQuery.getAllUsers(page, size)
                                    .stream()
                                    .map(userDtoMapper::toGetUserDto)
                                    .collect(Collectors.toList());
            } else {
                return getUsersQuery.getAllUsers()
                                    .stream()
                                    .map(userDtoMapper::toGetUserDto)
                                    .collect(Collectors.toList());
            }
        } else {
            if (page != null && size != null) {
                return getUsersQuery.getAllUsersByActive(active, page, size)
                                    .stream()
                                    .map(userDtoMapper::toGetUserDto)
                                    .collect(Collectors.toList());
            } else {
                return getUsersQuery.getAllUsersByActive(active)
                                    .stream()
                                    .map(userDtoMapper::toGetUserDto)
                                    .collect(Collectors.toList());
            }
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<GetUserDto> getUser(@PathVariable Long id) {
        return getUsersQuery.getUserById(new User.UserId(id))
                            .map(userDtoMapper::toGetUserDto)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(id)))
               ? ResponseEntity.noContent().build()
               : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<GetUserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto user) {
        return updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(id),
                                                                                    user.getFirstName(),
                                                                                    user.getLastName(),
                                                                                    user.getEmail()))
                                .map(userDtoMapper::toGetUserDto)
                                .map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/activate/{id}")
    ResponseEntity<?> activateUser(@PathVariable Long id) {
        return activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(id)))
                                  .map(userDtoMapper::toGetUserDto)
                                  .map(ResponseEntity::ok)
                                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/deactivate/{id}")
    ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        return deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(id)))
                                    .map(userDtoMapper::toGetUserDto)
                                    .map(ResponseEntity::ok)
                                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
