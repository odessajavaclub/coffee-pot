package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

@Data
public class GetUserDto {

    private final long id;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String role;

    private final boolean deactivated;
}
