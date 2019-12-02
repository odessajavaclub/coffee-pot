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

    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    @PostMapping
    User createUser(@Valid @RequestBody CreateUserDto user) {
        CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(user.getFirstName(),
                                                                                              user.getLastName(),
                                                                                              user.getEmail(),
                                                                                              user.getPassword(),
                                                                                              userDtoMapper.mapStringRoleToUserRole(user.getRole()));
        return createUserUseCase.createActivatedUser(command);
    }

    @GetMapping
    List<GetUserDto> getUsers() {
        return getUsersQuery.getUsers().stream()
                            .map(userDtoMapper::mapUserToGetUserDto)
                            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable Long id) {
        return getUsersQuery.getUser(new GetUsersQuery.UserQuery(new User.UserId(id)))
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
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {
        return updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(id),
                                                                                    user.getFirstName(),
                                                                                    user.getLastName(),
                                                                                    user.getEmail()))
                                .map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/activate/{id}")
    ResponseEntity<?> activateUser(@PathVariable Long id) {
        return activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(id)))
               ? ResponseEntity.ok().build()
               : ResponseEntity.notFound().build();
    }

    @PutMapping("/deactivate/{id}")
    ResponseEntity<?> deactivate(@PathVariable Long id) {
        return deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(id)))
               ? ResponseEntity.ok().build()
               : ResponseEntity.notFound().build();
    }
}
