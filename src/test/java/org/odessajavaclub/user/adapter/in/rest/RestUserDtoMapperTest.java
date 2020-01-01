package org.odessajavaclub.user.adapter.in.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestUserDtoMapperTest {

    private RestUserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = new RestUserDtoMapper();
    }

    @Test
    void toGetUserDto() {
        User user = User.withId(12345L, "Maxim", "Sashkin", "max@email.com", "pass123", UserRole.USER, true);
        GetUserDto expected = new GetUserDto(12345L, "Maxim", "Sashkin", "max@email.com", "user", true);
        GetUserDto actual = userDtoMapper.toGetUserDto(user);
        assertEquals(expected, actual);
    }
}
