package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.ActivateUserPort;
import org.odessajavaclub.user.application.port.out.DeactivateUserPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ActivationUserService implements ActivateUserUseCase, DeactivateUserUseCase {

    private final ActivateUserPort activateUserPort;

    private final DeactivateUserPort deactivateUserPort;

    @Override
    public boolean activateUser(ActivateUserCommand command) {
        return activateUserPort.activateUser(command.getUserId());
    }

    @Override
    public boolean deactivateUser(DeactivateUserCommand command) {
        return deactivateUserPort.deactivateUser(command.getUserId());
    }
}
