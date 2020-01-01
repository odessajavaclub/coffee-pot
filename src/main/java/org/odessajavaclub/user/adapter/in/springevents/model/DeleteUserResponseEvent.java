package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteUserResponseEvent extends ApplicationEvent {

    private final boolean removed;

    public DeleteUserResponseEvent(Object source, boolean removed) {
        super(source);
        this.removed = removed;
    }
}
