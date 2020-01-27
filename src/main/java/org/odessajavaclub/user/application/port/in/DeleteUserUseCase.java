package org.odessajavaclub.user.application.port.in;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface DeleteUserUseCase {

  boolean deleteUser(DeleteUserCommand command);

  @Value
  @RequiredArgsConstructor
  @Builder
  class DeleteUserCommand {

    @NotNull
    private final User.UserId id;
  }
}
