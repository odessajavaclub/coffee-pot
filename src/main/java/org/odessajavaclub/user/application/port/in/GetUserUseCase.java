package org.odessajavaclub.user.application.port.in;

import lombok.Builder;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;

public interface GetUserUseCase {

    User getUser(GetUserCriteria userId);

    @Builder
    class GetUserCriteria {

        private final UserId userId;

        private final String firstName;

        private final String lastName;
    }
}
