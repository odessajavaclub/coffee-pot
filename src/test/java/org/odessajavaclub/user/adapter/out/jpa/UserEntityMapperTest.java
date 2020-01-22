package org.odessajavaclub.user.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;

class UserEntityMapperTest {

  private UserEntityMapper userEntityMapper;

  @BeforeEach
  void setUp() {
    userEntityMapper = new UserEntityMapper();
  }

  @Test
  void toUserEntity() {
    User user = User.builder()
                    .id(new UserId(12345L))
                    .firstName("Maxim")
                    .lastName("Sashkin")
                    .email("maxs@email.com")
                    .password("pass123")
                    .role(UserRole.USER)
                    .active(true)
                    .build();
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
    User expected = User.builder()
                        .id(new UserId(12345L))
                        .firstName("Maxim")
                        .lastName("Sashkin")
                        .email("maxs@email.com")
                        .password("pass123")
                        .role(UserRole.USER)
                        .active(true)
                        .build();
    User actual = userEntityMapper.toUser(userEntity);
    assertEquals(expected, actual);
  }
}
