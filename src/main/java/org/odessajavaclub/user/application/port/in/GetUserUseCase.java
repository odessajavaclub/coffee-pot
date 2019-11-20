package org.odessajavaclub.user.application.port.in;

import lombok.Builder;
import org.odessajavaclub.user.domain.User;

public interface GetUserUseCase {

    User getUser(GetUserCriteria userId);

    @Builder
    class GetUserCriteria {

        private final User.UserId userId;

        private final String firstName;

        private final String lastName;
    }
}
