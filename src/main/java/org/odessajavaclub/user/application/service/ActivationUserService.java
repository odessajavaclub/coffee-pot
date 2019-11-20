package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.ActivateUserPort;
import org.odessajavaclub.user.application.port.out.DeactivateUserPort;

@RequiredArgsConstructor
class ActivationUserService implements ActivateUserUseCase, DeactivateUserUseCase {

    private final ActivateUserPort activateUserPort;

    private final DeactivateUserPort deactivateUserPort;

    @Override
    public void activateUser(ActivateUserCommand command) {
        activateUserPort.activateUser(command.getUserId());
    }

    @Override
    public void deactivateUser(DeactivateUserCommand command) {
        deactivateUserPort.deactivateUser(command.getUserId());
    }
}
