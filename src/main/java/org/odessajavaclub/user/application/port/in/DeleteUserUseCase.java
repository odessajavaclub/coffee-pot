package org.odessajavaclub.user.application.port.in;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface DeleteUserUseCase {

  boolean deleteUser(DeleteUserCommand command);

  @Value
  @Builder
  @AllArgsConstructor
  class DeleteUserCommand {

    @NotNull
    private final User.UserId userId;
  }
}
