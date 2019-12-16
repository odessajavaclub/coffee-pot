package org.odessajavaclub.user.application.event;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = false)
public class UserUpdatedEvent extends ApplicationEvent {

    private final User oldUser;

    private final User newUser;

    public UserUpdatedEvent(User oldUser, User newUser) {
        super(Arrays.asList(oldUser, newUser));
        this.oldUser = oldUser;
        this.newUser = newUser;
    }

    public User getOldUser() {
        return oldUser;
    }

    public User getNewUser() {
        return newUser;
    }
}
