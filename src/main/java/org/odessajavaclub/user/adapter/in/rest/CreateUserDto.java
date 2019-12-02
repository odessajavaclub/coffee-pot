package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
class CreateUserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String role;

    @NotBlank
    private String password;
}
