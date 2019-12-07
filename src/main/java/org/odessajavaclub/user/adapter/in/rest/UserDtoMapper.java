package org.odessajavaclub.user.adapter.in.rest;

import lombok.NoArgsConstructor;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

@NoArgsConstructor
public class UserDtoMapper {

    private static final long UNDEFINED_ID = -1L;

    public UserRole toUserRole(String role) {
        return UserRole.fromName(role);
    }

    public GetUserDto toGetUserDto(User user) {
        return new GetUserDto(user.getId()
                                  .map(User.UserId::getValue)
                                  .orElse(UNDEFINED_ID),
                              user.getFirstName(),
                              user.getLastName(),
                              user.getEmail(),
                              user.getRole().getName(),
                              user.isActive());
    }
}
