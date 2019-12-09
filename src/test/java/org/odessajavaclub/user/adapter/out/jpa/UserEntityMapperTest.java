package org.odessajavaclub.user.adapter.out.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityMapperTest {

    private UserEntityMapper userEntityMapper;

    @BeforeEach
    void setUp() {
        userEntityMapper = new UserEntityMapper();
    }

    @Test
    void toUserEntity() {
        User user = User.withId(12345L, "Maxim", "Sashkin", "max@email.com", "pass123", UserRole.USER, true);
        UserEntity expected = new UserEntity(12345L,
                                             "Maxim",
                                             "Sashkin",
                                             "max@email.com",
                                             "pass123",
                                             UserRole.USER,
                                             true);
        UserEntity actual = userEntityMapper.toUserEntity(user);
        assertEquals(expected, actual);
    }

    @Test
    void toUser() {
        UserEntity userEntity = new UserEntity(12345L,
                                               "Maxim",
                                               "Sashkin",
                                               "max@email.com",
                                               "pass123",
                                               UserRole.USER,
                                               true);
        User expected = User.withId(12345L, "Maxim", "Sashkin", "max@email.com", "pass123", UserRole.USER, true);
        User actual = userEntityMapper.toUser(userEntity);
        assertEquals(expected, actual);
    }
}
