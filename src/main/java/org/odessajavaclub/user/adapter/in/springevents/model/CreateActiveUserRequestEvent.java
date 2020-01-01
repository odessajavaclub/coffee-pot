package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateActiveUserRequestEvent extends ApplicationEvent {

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String password;

    private final UserRole role;

    public CreateActiveUserRequestEvent(Object source,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        String password,
                                        UserRole role) {
        super(source);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
