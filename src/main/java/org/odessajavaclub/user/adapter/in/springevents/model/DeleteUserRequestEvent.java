package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteUserRequestEvent extends ApplicationEvent {

    private final long id;

    public DeleteUserRequestEvent(Object source, long id) {
        super(source);
        this.id = id;
    }
}
