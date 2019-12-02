package org.odessajavaclub.user.adapter.in.rest;

import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

class UserDtoMapper {

    UserRole mapStringRoleToUserRole(String role) {
        return UserRole.fromName(role);
    }

    GetUserDto mapUserToGetUserDto(User user) {
        return new GetUserDto(user.getId()
                                  .map(User.UserId::getValue)
                                  .orElse(0L),
                              user.getFirstName(),
                              user.getLastName(),
                              user.getRole().getName());
    }
}
