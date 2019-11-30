package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

@Data
class UpdateUserDto {

    private String firstName;

    private String lastName;

    private String email;
}
