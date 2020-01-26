package org.odessajavaclub.user.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.domain.User.UserId;

class UserIdMapperTest {

  private UserIdMapper userIdMapper;

  @BeforeEach
  void setUp() {
    userIdMapper = new UserIdMapper();
  }

  @Test
  void asUserId() {
    UserId expected = new UserId(12345L);

    UserId actual = userIdMapper.asUserId(12345L);

    assertEquals(expected, actual);
  }

  @Test
  void asUserIdFromNull() {
    assertNull(userIdMapper.asUserId(null));
  }

  @Test
  void asId() {
    Long expected = 6789L;

    Long actual = userIdMapper.asId(new UserId(6789L));

    assertEquals(expected, actual);
  }

  @Test
  void asIdFromNull() {
    assertNull(userIdMapper.asId(null));
  }
}
