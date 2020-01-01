package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersResponseEvent extends ApplicationEvent {

    private final List<GetUserDto> users;

    public GetUsersResponseEvent(Object source, List<GetUserDto> users) {
        super(source);
        this.users = users;
    }
}
