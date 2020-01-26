package org.odessajavaclub.user.adapter.out.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.odessajavaclub.user.adapter.out.jpa.UserRepositoryTest.TestConfig;
import org.odessajavaclub.user.adapter.out.jpa.mapper.UserEntityMapper;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;
import org.odessajavaclub.user.shared.UserIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(TestConfig.class)
@Sql("user-repository-test-data.sql")
class UserRepositoryTest {

  @TestConfiguration
  static class TestConfig {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Bean
    UserEntityMapper userEntityMapper() {
      return Mappers.getMapper(UserEntityMapper.class);
    }

    @Bean
    UserIdMapper userIdMapper() {
      return new UserIdMapper();
    }

    @Bean
    UserRepository userRepository() {
      return new UserRepository(userJpaRepository, userEntityMapper());
    }
  }

  @Autowired
  private UserRepository userRepository;

  @Test
  void createReadonlyUser() {
    User newUser = userRepository.createUser(User.builder()
                                                 .firstName("New")
                                                 .lastName("User")
                                                 .email("newuser@email.com")
                                                 .password("{noop}newuser1234")
                                                 .role(UserRole.READONLY)
                                                 .active(true)
                                                 .build());

    List<User> actual = userRepository.loadAllUsers();

    assertNotNull(newUser);
    assertEquals(5, actual.size());
  }

  @Test
  void createAdminUser() {
    User newUser = userRepository.createUser(User.builder()
                                                 .firstName("New")
                                                 .lastName("User")
                                                 .email("newuser@email.com")
                                                 .password("{noop}newuser1234")
                                                 .role(UserRole.ADMIN)
                                                 .active(true)
                                                 .build());

    List<User> actual = userRepository.loadAllUsers();

    assertNotNull(newUser);
    assertEquals(5, actual.size());
  }

  @Test
  void createRegularUser() {
    User newUser = userRepository.createUser(User.builder()
                                                 .firstName("New")
                                                 .lastName("User")
                                                 .email("newuser@email.com")
                                                 .password("{noop}newuser1234")
                                                 .role(UserRole.USER)
                                                 .active(true)
                                                 .build());

    List<User> actual = userRepository.loadAllUsers();

    assertNotNull(newUser);
    assertEquals(5, actual.size());
  }

  @Test
  void deleteExistingUser() {
    boolean actual = userRepository.deleteUser(new User.UserId(3L));

    List<User> actualUsers = userRepository.loadAllUsers();

    assertTrue(actual);
    assertEquals(3, actualUsers.size());
  }

  @Test
  void deleteNonExistingUser() {
    boolean actual = userRepository.deleteUser(new User.UserId(999L));

    List<User> actualUsers = userRepository.loadAllUsers();

    assertFalse(actual);
    assertEquals(4, actualUsers.size());
  }

  @Test
  void loadAllUsers() {
    List<User> actual = userRepository.loadAllUsers();

    assertEquals(4, actual.size());
  }

  @Test
  void loadAllUsersPaged() {
    List<User> actual = userRepository.loadAllUsers(0, 2);

    assertEquals(2, actual.size());
  }

  @Test
  void loadAllActiveUsers() {
    List<User> actual = userRepository.loadAllUsersByActive(true);

    assertEquals(3, actual.size());
  }

  @Test
  void loadAllActiveUsersPaged() {
    List<User> actual = userRepository.loadAllUsersByActive(true, 0, 2);

    assertEquals(2, actual.size());
  }

  @Test
  void loadAllInactiveUsers() {
    List<User> actual = userRepository.loadAllUsersByActive(false);

    assertEquals(1, actual.size());
  }

  @Test
  void loadExistingUser() {
    Optional<User> actual = userRepository.loadUser(new User.UserId(2L));

    User expected = User.builder()
                        .id(new UserId(2L))
                        .firstName("Alexander")
                        .lastName("Bevziuk")
                        .email("alexb@email.com")
                        .password("{noop}alexb1234")
                        .role(UserRole.USER)
                        .active(true)
                        .build();

    assertEquals(Optional.of(expected), actual);
  }

  @Test
  void loadNonExistingUser() {
    Optional<User> actual = userRepository.loadUser(new User.UserId(999L));

    assertTrue(actual.isEmpty());
  }

  @Test
  void updateUserWithNullId() {
    assertThrows(UserIdIsAbsentException.class,
                 () -> userRepository.updateUser(User.builder()
                                                     .firstName("New")
                                                     .lastName("User")
                                                     .email("newuser@email.com")
                                                     .password("{noop}newuser1234")
                                                     .role(UserRole.READONLY)
                                                     .active(true)
                                                     .build()));
  }

  @Test
  void updateUserWithNormalId() {
    User expected = User.builder()
                        .id(new UserId(1L))
                        .firstName("New")
                        .lastName("User")
                        .email("newuser@email.com")
                        .password("{noop}newuser1234")
                        .role(UserRole.READONLY)
                        .active(true)
                        .build();

    User updatedUser = userRepository.updateUser(expected);

    List<User> actualUsers = userRepository.loadAllUsers();

    assertEquals(4, actualUsers.size());
    assertEquals(expected, updatedUser);
  }
}
