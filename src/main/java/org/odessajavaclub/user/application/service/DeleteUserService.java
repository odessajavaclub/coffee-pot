package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteUserService implements DeleteUserUseCase {

  private final DeleteUserPort deleteUserPort;

  @Override
  public boolean deleteUser(DeleteUserCommand command) {
    return deleteUserPort.deleteUser(command.getUserId());
  }
}
