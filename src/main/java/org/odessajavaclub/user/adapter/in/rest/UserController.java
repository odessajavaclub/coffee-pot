package org.odessajavaclub.user.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersUseCase;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    private final GetUsersUseCase getUsersUseCase;

    private final GetUserUseCase getUserUseCase;

    private final DeleteUserUseCase deleteUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping
    User createUser(@RequestBody UserDto user) {
        CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(user.getFirstName(),
                                                                                              user.getLastName());
        return createUserUseCase.createActivatedUser(command);
    }

    @GetMapping
    List<User> getUsers() {
        return getUsersUseCase.getUsers();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable Long id) {
        return getUserUseCase.getUser(new GetUserUseCase.GetUserCommand(new User.UserId(id)))
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(id)))
               ? ResponseEntity.noContent().build()
               : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
        return updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(id),
                                                                                    user.getFirstName(),
                                                                                    user.getLastName()))
                                .map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
