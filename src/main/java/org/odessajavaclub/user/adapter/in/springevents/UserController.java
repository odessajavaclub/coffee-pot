package org.odessajavaclub.user.adapter.in.springevents;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersRequestEvent;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUsersResponseEvent;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class UserController {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final GetUsersQuery getUsersQuery;

    @EventListener
    public void getUsers(GetUsersRequestEvent requestEvent) {
        getUsersQuery.getAllUsersByActive(requestEvent.isActive(), requestEvent.getPage(), requestEvent.getSize())
                     .forEach(u -> applicationEventPublisher.publishEvent(new GetUsersResponseEvent(this, u)));
    }
}
