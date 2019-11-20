package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;

@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort createUserPort;

    @Override
    public boolean createActivatedUser(CreateUserCommand command) {
        return createUser(command, false);
    }

    @Override
    public boolean createDeactivatedUser(CreateUserCommand command) {
        return createUser(command, true);
    }

    private boolean createUser(CreateUserCommand command, boolean isDeactivated) {
        checkFirstNameIsNotBlank(command);
        checkLastNameIsNotBlank(command);

        CreateUserCommandToUserMapper createUserCommandToUserMapper = new CreateUserCommandToUserMapper();
        User user = createUserCommandToUserMapper.map(command, isDeactivated);

        return createUserPort.createUser(user);
    }

    private void checkFirstNameIsNotBlank(CreateUserCommand command) {
        if (command.getFirstName().isBlank()) {
            throw new FirstNameIsBlankException();
        }
    }

    private void checkLastNameIsNotBlank(CreateUserCommand command) {
        if (command.getLastName().isBlank()) {
            throw new LastNameIsBlankException();
        }
    }
}
