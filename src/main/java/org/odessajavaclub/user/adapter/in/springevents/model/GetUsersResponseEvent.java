package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersResponseEvent extends ApplicationEvent {

    private final User user;

    public GetUsersResponseEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
