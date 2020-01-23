package org.odessajavaclub.user.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class ActivationUserService implements ActivateUserUseCase, DeactivateUserUseCase {

  private final LoadUsersPort loadUsersPort;

  private final UpdateUserPort updateUserPort;

  private final Validating validating;

  @Override
  public Optional<User> activateUser(ActivateUserCommand command) {
    validating.validate(command);
    return loadUsersPort.loadUser(command.getUserId())
                        .map(u -> u.toBuilder().active(true).build())
                        .map(updateUserPort::updateUser);
  }

  @Override
  public Optional<User> deactivateUser(DeactivateUserCommand command) {
    validating.validate(command);
    return loadUsersPort.loadUser(command.getUserId())
                        .map(u -> u.toBuilder().active(false).build())
                        .map(updateUserPort::updateUser);
  }
}
