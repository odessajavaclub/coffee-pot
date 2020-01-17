package org.odessajavaclub.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserRoleTest {

  @Test
  void fromNameAdmin() {
    assertEquals(UserRole.ADMIN, UserRole.fromName("admin"));
  }

  @Test
  void fromNameUser() {
    assertEquals(UserRole.USER, UserRole.fromName("user"));
  }

  @Test
  void fromNameReadOnly() {
    assertEquals(UserRole.READONLY, UserRole.fromName("readonly"));
  }

  @Test
  void fromNameUnknown() {
    assertThrows(IllegalArgumentException.class, () -> UserRole.fromName("unknown"));
  }
}
