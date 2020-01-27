package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteUserService implements DeleteUserUseCase {

  private final DeleteUserPort deleteUserPort;

  private final Validating validating;

  @Override
  public boolean deleteUser(DeleteUserCommand command) {
    validating.validate(command);
    return deleteUserPort.deleteUser(command.getId());
  }
}
