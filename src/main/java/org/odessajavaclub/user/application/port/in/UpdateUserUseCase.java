package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.domain.User;

public interface UpdateUserUseCase {

  Optional<User> updateUser(UpdateUserCommand command);

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  class UpdateUserCommand {

    @NotNull
    private final User.UserId id;

    private String newFirstName;

    private String newLastName;

    private String newEmail;
  }
}
