package org.odessajavaclub.user.application.service;

import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.domain.User;

class CreateUserCommandToUserMapper {

    User map(CreateUserUseCase.CreateUserCommand command, boolean isDeactivated) {
        return User.withoutId(command.getFirstName(), command.getLastName(), isDeactivated);
    }
}
