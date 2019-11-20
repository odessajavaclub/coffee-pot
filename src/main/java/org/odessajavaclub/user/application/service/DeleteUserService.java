package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;

@RequiredArgsConstructor
class DeleteUserService implements DeleteUserUseCase {

    private final DeleteUserPort deleteUserPort;

    @Override
    public void deleteUser(DeleteUserCommand command) {
        deleteUserPort.deleteUser(command.getUserId());
    }
}
