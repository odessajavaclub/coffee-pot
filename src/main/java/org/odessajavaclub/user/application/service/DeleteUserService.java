package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.event.UserDeletedEvent;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteUserService implements DeleteUserUseCase {

    private final DeleteUserPort deleteUserPort;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public boolean deleteUser(DeleteUserCommand command) {
        boolean userDeleted = deleteUserPort.deleteUser(command.getUserId());
        if (userDeleted) {
            applicationEventPublisher.publishEvent(new UserDeletedEvent(command.getUserId()));
        }
        return userDeleted;
    }
}
