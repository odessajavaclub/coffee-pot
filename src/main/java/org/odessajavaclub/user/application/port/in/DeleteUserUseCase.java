package org.odessajavaclub.user.application.port.in;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.domain.User;

public interface DeleteUserUseCase {

  boolean deleteUser(DeleteUserCommand command);

  @Data
  @Builder
  @RequiredArgsConstructor
  class DeleteUserCommand {

    @NotNull
    private final User.UserId userId;
  }
}
