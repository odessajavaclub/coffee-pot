package org.odessajavaclub.user.adapter.in.rest;

import org.odessajavaclub.user.domain.UserRole;

class UserDtoMapper {

    UserRole mapRole(String role) {
        return UserRole.fromName(role);
    }
}
