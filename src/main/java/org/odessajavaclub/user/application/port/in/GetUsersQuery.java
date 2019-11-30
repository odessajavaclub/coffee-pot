package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface GetUsersQuery {

    List<User> getUsers();

    Optional<User> getUser(UserQuery userQuery);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UserQuery extends SelfValidating<UserQuery> {

        @NotNull
        private final User.UserId userId;

        public UserQuery(User.UserId userId) {
            this.userId = userId;
            this.validateSelf();
        }
    }
}
