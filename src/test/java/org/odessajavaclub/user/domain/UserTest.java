package org.odessajavaclub.user.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.odessajavaclub.user.domain.User.UserId;

class UserTest {

  @Test
  void equals() {
    User user1 = User.builder()
                     .id(new UserId(12345L))
                     .firstName("Maxim")
                     .lastName("Sashkin")
                     .email("maxs@email.com")
                     .password("pass123")
                     .role(UserRole.USER)
                     .active(true)
                     .build();
    User user2 = User.builder()
                     .id(new UserId(12345L))
                     .firstName("Maxim")
                     .lastName("Sashkin")
                     .email("maxs@email.com")
                     .password("pass123")
                     .role(UserRole.USER)
                     .active(true)
                     .build();

    assertEquals(user1, user2);
  }

  @Test
  void withoutId() {
    User actual = User.builder()
                      .firstName("Maxim")
                      .lastName("Sashkin")
                      .email("maxs@email.com")
                      .password("pass123")
                      .role(UserRole.USER)
                      .active(true)
                      .build();

    assertAll(
        () -> assertEquals("Maxim", actual.getFirstName()),
        () -> assertEquals("Sashkin", actual.getLastName()),
        () -> assertEquals("maxs@email.com", actual.getEmail()),
        () -> assertEquals("pass123", actual.getPassword()),
        () -> assertEquals(UserRole.USER, actual.getRole()),
        () -> assertTrue(actual.isActive()));
  }

  @Test
  void withIdIfIdIsNotNull() {
    User actual = User.builder()
                      .id(new UserId(12345L))
                      .firstName("Maxim")
                      .lastName("Sashkin")
                      .email("maxs@email.com")
                      .password("pass123")
                      .role(UserRole.USER)
                      .active(true)
                      .build();

    assertAll(
        () -> assertEquals(1234L, actual.getIdOptional().map(User.UserId::getValue).orElse(-1L)),
        () -> assertEquals("Maxim", actual.getFirstName()),
        () -> assertEquals("Sashkin", actual.getLastName()),
        () -> assertEquals("maxs@email.com", actual.getEmail()),
        () -> assertEquals("pass123", actual.getPassword()),
        () -> assertEquals(UserRole.USER, actual.getRole()),
        () -> assertTrue(actual.isActive()));
  }

  @Test
  void withIdIfIdIsNull() {
    User actual = User.builder()
                      .id(null)
                      .firstName("Maxim")
                      .lastName("Sashkin")
                      .email("maxs@email.com")
                      .password("pass123")
                      .role(UserRole.USER)
                      .active(true)
                      .build();

    assertAll(
        () -> assertTrue(actual.getIdOptional().isEmpty()),
        () -> assertEquals("Maxim", actual.getFirstName()),
        () -> assertEquals("Sashkin", actual.getLastName()),
        () -> assertEquals("maxs@email.com", actual.getEmail()),
        () -> assertEquals("pass123", actual.getPassword()),
        () -> assertEquals(UserRole.USER, actual.getRole()),
        () -> assertTrue(actual.isActive()));
  }
}
