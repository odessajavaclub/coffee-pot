package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
class UpdateUserDto {

    private String firstName;

    private String lastName;

    @Email
    private String email;
}
