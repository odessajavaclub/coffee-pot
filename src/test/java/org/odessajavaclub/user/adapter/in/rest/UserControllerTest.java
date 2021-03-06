package org.odessajavaclub.user.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.odessajavaclub.auth.AuthenticationFacade;
import org.odessajavaclub.user.adapter.in.rest.UserControllerTest.TestConfig;
import org.odessajavaclub.user.adapter.in.rest.mapper.UserRestMapper;
import org.odessajavaclub.user.adapter.in.rest.mapper.UserRoleRestMapper;
import org.odessajavaclub.user.adapter.in.rest.model.CreateUserDto;
import org.odessajavaclub.user.adapter.in.rest.model.GetUserDto;
import org.odessajavaclub.user.adapter.in.rest.model.UpdateUserDto;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.User.UserId;
import org.odessajavaclub.user.domain.UserRole;
import org.odessajavaclub.user.shared.UserIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
@Import(TestConfig.class)
class UserControllerTest {

  @TestConfiguration
  static class TestConfig {

    @Bean
    UserRestMapper userRestMapper() {
      return Mappers.getMapper(UserRestMapper.class);
    }

    @Bean
    UserRoleRestMapper userRoleRestMapper() {
      return new UserRoleRestMapper();
    }

    @Bean
    UserIdMapper userIdMapper() {
      return new UserIdMapper();
    }
  }

  private static final String USER_NAME = "test_user";

  private static final String PASSWORD = "test_password";

  private static final int DEFAULT_PAGE = 0;

  private static final int DEFAULT_SIZE = 100;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CreateUserUseCase createUserUseCase;

  @MockBean
  private GetUsersQuery getUsersQuery;

  @MockBean
  private DeleteUserUseCase deleteUserUseCase;

  @MockBean
  private UpdateUserUseCase updateUserUseCase;

  @MockBean
  private ActivateUserUseCase activateUserUseCase;

  @MockBean
  private DeactivateUserUseCase deactivateUserUseCase;

  @MockBean
  private AuthenticationFacade authenticationFacade;

  @BeforeEach
  public void setUp() {
    TestingAuthenticationToken authentication = new TestingAuthenticationToken(USER_NAME,
                                                                               PASSWORD,
                                                                               "USER");
    when(authenticationFacade.getAuthentication()).thenReturn(authentication);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnOkIfUserIsOk() throws Exception {
    when(createUserUseCase.createActiveUser(new CreateUserUseCase.CreateUserCommand("Maxim",
                                                                                    "Sashkin",
                                                                                    "max@email.com",
                                                                                    "pass123",
                                                                                    UserRole.ADMIN)))
        .thenReturn(User.builder()
                        .id(new UserId(1L))
                        .firstName("Maxim")
                        .lastName("Sashkin")
                        .email("max@email.com")
                        .password("pass123")
                        .role(UserRole.ADMIN)
                        .active(true)
                        .build());

    String expected = objectMapper.writeValueAsString(new GetUserDto(1L,
                                                                     "Maxim",
                                                                     "Sashkin",
                                                                     "max@email.com",
                                                                     "admin",
                                                                     true));

    CreateUserDto content = new CreateUserDto("Maxim",
                                              "Sashkin",
                                              "max@email.com",
                                              "pass123",
                                              "admin");

    String actual = mockMvc.perform(post("/users/")
                                        .contentType("application/json")
                                        .content(objectMapper.writeValueAsString(content)))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnBadRequestIfFirstNameIsBlank() throws Exception {
    CreateUserDto content = new CreateUserDto("", "Sashkin", "max@email.com", "pass123", "admin");

    mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnBadRequestIfLastNameIsBlank() throws Exception {
    CreateUserDto content = new CreateUserDto("Max", "", "max@email.com", "pass123", "admin");

    mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnBadRequestIfPasswordIsBlank() throws Exception {
    CreateUserDto content = new CreateUserDto("Max", "Sashkin", "max@email.com", "", "admin");

    mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnBadRequestIfRoleIsBlank() throws Exception {
    CreateUserDto content = new CreateUserDto("Max", "Sashkin", "max@email.com", "pass123", "");

    mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void createUserShouldReturnBadRequestIfEmailIsNotValid() throws Exception {
    CreateUserDto content = new CreateUserDto("Max", "Sashkin", "", "pass123", "admin");

    mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getUsersIfUsersExist() throws Exception {
    when(getUsersQuery.getAllUsers(DEFAULT_PAGE, DEFAULT_SIZE))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(true)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(false)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             true),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             false)));

    String actual = mockMvc.perform(get("/users/"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getUsersPagedIfUsersExist() throws Exception {
    when(getUsersQuery.getAllUsers(1, 2))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(true)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(false)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             true),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             false)));

    String actual = mockMvc.perform(get("/users/")
                                        .param("page", "1")
                                        .param("size", "2"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getUserIfUserExists() throws Exception {
    when(getUsersQuery.getUserById(new User.UserId(666L)))
        .thenReturn(Optional.of(User.builder()
                                    .id(new UserId(666L))
                                    .firstName("John")
                                    .lastName("Johnovich")
                                    .email("john@email.com")
                                    .password("pass1")
                                    .role(UserRole.USER)
                                    .active(true)
                                    .build()));

    String expected = objectMapper.writeValueAsString(new GetUserDto(666L,
                                                                     "John",
                                                                     "Johnovich",
                                                                     "john@email.com",
                                                                     "user",
                                                                     true));

    String actual = mockMvc.perform(get("/users/666"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getUserIfUserDoesNotExist() throws Exception {
    when(getUsersQuery.getUserById(new User.UserId(666L)))
        .thenReturn(Optional.empty());

    mockMvc.perform(get("/users/666"))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void deleteUserIfUserExists() throws Exception {
    when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(777L))))
        .thenReturn(true);

    mockMvc.perform(delete("/users/777"))
           .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void deleteUserIfUserDoesNotExist() throws Exception {
    when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(777L))))
        .thenReturn(false);

    mockMvc.perform(delete("/users/777"))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void updateUserIfUserExists() throws Exception {
    when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(888L),
                                                                              "New First Name",
                                                                              "New Last Name",
                                                                              "new@email.com")))
        .thenReturn(Optional.of(User.builder()
                                    .id(new UserId(888L))
                                    .firstName("New First Name")
                                    .lastName("New Last Name")
                                    .email("new@email.com")
                                    .password("pass1")
                                    .role(UserRole.USER)
                                    .active(true)
                                    .build()));

    String expected = objectMapper.writeValueAsString(new GetUserDto(888L,
                                                                     "New First Name",
                                                                     "New Last Name",
                                                                     "new@email.com",
                                                                     "user",
                                                                     true));
    UpdateUserDto content = new UpdateUserDto("New First Name", "New Last Name", "new@email.com");
    String actual = mockMvc.perform(put("/users/888")
                                        .contentType("application/json")
                                        .content(objectMapper.writeValueAsString(content)))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void updateUserIfUserDoesNotExist() throws Exception {
    when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(888L),
                                                                              "New First Name",
                                                                              "New Last Name",
                                                                              "new@email.com")))
        .thenReturn(Optional.empty());

    UpdateUserDto content = new UpdateUserDto("New First Name", "New Last Name", "new@email.com");
    mockMvc.perform(put("/users/888")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(content)))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void activateUserIfUserExists() throws Exception {
    when(activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L))))
        .thenReturn(Optional.of(User.builder()
                                    .id(new UserId(123L))
                                    .firstName("First name 1")
                                    .lastName("Last name 1")
                                    .email("one@email.com")
                                    .password("pass1")
                                    .role(UserRole.ADMIN)
                                    .active(true)
                                    .build()));

    mockMvc.perform(put("/users/activate/123"))
           .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void activateUserIfUserDoesNotExist() throws Exception {
    when(activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L))))
        .thenReturn(Optional.empty());

    mockMvc.perform(put("/users/activate/123"))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void deactivateUserIfUserExists() throws Exception {
    when(deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(567L))))
        .thenReturn(Optional.of(User.builder()
                                    .id(new UserId(567L))
                                    .firstName("First name 1")
                                    .lastName("Last name 1")
                                    .email("one@email.com")
                                    .password("pass1")
                                    .role(UserRole.ADMIN)
                                    .active(false)
                                    .build()));

    mockMvc.perform(put("/users/deactivate/567"))
           .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void deactivateUserIfUserDoesNotExist() throws Exception {
    when(deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(
        567L))))
        .thenReturn(Optional.empty());

    mockMvc.perform(put("/users/deactivate/567"))
           .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getActiveUsersOnly() throws Exception {
    when(getUsersQuery.getAllUsersByActive(true, DEFAULT_PAGE, DEFAULT_SIZE))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(true)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(true)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             true),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             true)));
    String actual = mockMvc.perform(get("/users")
                                        .param("active", "true"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getActiveUsersOnlyPaged() throws Exception {
    when(getUsersQuery.getAllUsersByActive(true, 1, 2))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(true)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(true)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             true),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             true)));
    String actual = mockMvc.perform(get("/users")
                                        .param("active", "true")
                                        .param("page", "1")
                                        .param("size", "2"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getInactiveUsersOnly() throws Exception {
    when(getUsersQuery.getAllUsersByActive(false, DEFAULT_PAGE, DEFAULT_SIZE))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(false)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(false)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             false),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             false)));
    String actual = mockMvc.perform(get("/users")
                                        .param("active", "false"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @WithMockUser(username = USER_NAME)
  void getInactiveUsersOnlyPaged() throws Exception {
    when(getUsersQuery.getAllUsersByActive(false, 1, 2))
        .thenReturn(List.of(User.builder()
                                .id(new UserId(1L))
                                .firstName("First name 1")
                                .lastName("Last name 1")
                                .email("one@email.com")
                                .password("pass1")
                                .role(UserRole.ADMIN)
                                .active(false)
                                .build(),
                            User.builder()
                                .id(new UserId(2L))
                                .firstName("First name 2")
                                .lastName("Last name 2")
                                .email("two@email.com")
                                .password("pass2")
                                .role(UserRole.USER)
                                .active(false)
                                .build()));

    String expected = objectMapper.writeValueAsString(List.of(new GetUserDto(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "admin",
                                                                             false),
                                                              new GetUserDto(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "user",
                                                                             false)));
    String actual = mockMvc.perform(get("/users")
                                        .param("active", "false")
                                        .param("page", "1")
                                        .param("size", "2"))
                           .andExpect(status().isOk())
                           .andReturn()
                           .getResponse()
                           .getContentAsString();
    assertEquals(expected, actual);
  }
}
