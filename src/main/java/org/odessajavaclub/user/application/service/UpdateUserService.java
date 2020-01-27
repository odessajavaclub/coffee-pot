package org.odessajavaclub.user.application.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateUserService implements UpdateUserUseCase {

  private final LoadUsersPort loadUserPort;

  private final UpdateUserPort updateUserPort;

  private final Validating validating;

  @Override
  public Optional<User> updateUser(UpdateUserCommand command) {
    validating.validate(command);
    User.UserId userId = Objects.requireNonNull(command.getId(), "User id must not be null");
    return loadUserPort.loadUser(userId)
                       .map(existingUser -> toUpdatedUser(command, existingUser))
                       .map(updateUserPort::updateUser);
  }

  private User toUpdatedUser(UpdateUserCommand command, User existingUser) {
    UserBuilder userBuilder = existingUser.toBuilder();
    if (command.getNewFirstName() != null) {
      userBuilder.firstName(command.getNewFirstName());
    }
    if (command.getNewLastName() != null) {
      userBuilder.lastName(command.getNewLastName());
    }
    if (command.getNewEmail() != null) {
      userBuilder.email(command.getNewEmail());
    }
    return userBuilder.build();
  }
}
