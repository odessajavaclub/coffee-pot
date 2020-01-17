package org.odessajavaclub.user.application.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateUserService implements UpdateUserUseCase {

  private final LoadUsersPort loadUserPort;

  private final UpdateUserPort updateUserPort;

  @Override
  public Optional<User> updateUser(UpdateUserCommand command) {
    User.UserId userId = Objects.requireNonNull(command.getId(), "User id must not be null");

    return loadUserPort.loadUser(userId).map(user -> User.from(user,
                                                               command.getNewFirstName(),
                                                               command.getNewLastName(),
                                                               command.getNewEmail()))
                       .map(updateUserPort::updateUser);
  }
}
