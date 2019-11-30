package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class CreateUserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String role;

    @NotBlank
    private String password;
}
