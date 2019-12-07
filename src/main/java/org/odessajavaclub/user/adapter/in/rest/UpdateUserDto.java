package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
class UpdateUserDto {

    private String firstName;

    private String lastName;

    @Email
    private String email;
}
