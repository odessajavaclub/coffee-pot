package org.odessajavaclub.user.application.event;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = false)
public class UserDeletedEvent extends ApplicationEvent {

    private final User.UserId userId;

    public UserDeletedEvent(User.UserId userId) {
        super(userId);
        this.userId = userId;
    }

    public User.UserId getUserId() {
        return userId;
    }
}
