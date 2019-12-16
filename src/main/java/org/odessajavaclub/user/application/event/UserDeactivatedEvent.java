package org.odessajavaclub.user.application.event;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = false)
public class UserDeactivatedEvent extends ApplicationEvent {

    private final User user;

    public UserDeactivatedEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
