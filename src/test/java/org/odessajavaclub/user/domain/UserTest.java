package org.odessajavaclub.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void equals() {
        User user1 = User.withId(12345L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);
        User user2 = User.withId(12345L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        assertEquals(user1, user2);
    }

    @Test
    void withoutId() {
        User actual = User.withoutId("Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

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
        User actual = User.withId(1234L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        assertAll(
                () -> assertEquals(1234L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void withIdIfIdIsNull() {
        User actual = User.withId(null, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        assertAll(
                () -> assertTrue(actual.getId().isEmpty()),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithUserId() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, new User.UserId(456L));

        assertAll(
                () -> assertEquals(456L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithFirstNameLastNameEmail() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, "New First Name", "New Last Name", "new@email.com");

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("New First Name", actual.getFirstName()),
                () -> assertEquals("New Last Name", actual.getLastName()),
                () -> assertEquals("new@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithFirstNameLastNameEmailIfFirstNameIsNull() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, null, "New Last Name", "new@email.com");

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("New Last Name", actual.getLastName()),
                () -> assertEquals("new@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithFirstNameLastNameEmailIfLastNameIsNull() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, "New First Name", null, "new@email.com");

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("New First Name", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("new@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithFirstNameLastNameEmailIfEmailIsNull() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, "New First Name", "New Last Name", null);

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("New First Name", actual.getFirstName()),
                () -> assertEquals("New Last Name", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithActiveTrue() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, false);

        User actual = User.from(input, true);

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertTrue(actual.isActive()));
    }

    @Test
    void fromWithActiveFalse() {
        User input = User.withId(123L, "Maxim", "Sashkin", "maxs@email.com", "pass123", UserRole.USER, true);

        User actual = User.from(input, false);

        assertAll(
                () -> assertEquals(123L, actual.getId().map(User.UserId::getValue).orElse(-1L)),
                () -> assertEquals("Maxim", actual.getFirstName()),
                () -> assertEquals("Sashkin", actual.getLastName()),
                () -> assertEquals("maxs@email.com", actual.getEmail()),
                () -> assertEquals("pass123", actual.getPassword()),
                () -> assertEquals(UserRole.USER, actual.getRole()),
                () -> assertFalse(actual.isActive()));
    }
}
