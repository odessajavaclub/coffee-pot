package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.NoArgsConstructor;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class SpringEventUserDtoMapper {

    private static final long UNDEFINED_ID = -1L;

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
