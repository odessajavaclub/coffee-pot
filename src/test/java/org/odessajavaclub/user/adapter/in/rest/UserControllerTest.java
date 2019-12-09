package org.odessajavaclub.user.adapter.in.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.auth.AuthenticationFacade;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeleteUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.config.UserConfig;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(UserConfig.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String USER_NAME = "test_user";
    private static final String PASSWORD = "test_password";

    @Autowired
    private MockMvc mockMvc;

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
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(USER_NAME, PASSWORD, "USER");

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
                .thenReturn(User.withId(1L,
                                        "Maxim",
                                        "Sashkin",
                                        "max@email.com",
                                        "pass123",
                                        UserRole.ADMIN,
                                        true));

        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{\n" +
                                                 "  \"firstName\": \"Maxim\",\n" +
                                                 "  \"lastName\": \"Sashkin\",\n" +
                                                 "  \"email\": \"max@email.com\",\n" +
                                                 "  \"password\": \"pass123\",\n" +
                                                 "  \"role\": \"admin\"\n" +
                                                 "}"))
               .andExpect(status().isOk())
               .andExpect(content().json("{\n" +
                                                 "  \"firstName\": \"Maxim\",\n" +
                                                 "  \"lastName\": \"Sashkin\",\n" +
                                                 "  \"email\": \"max@email.com\",\n" +
                                                 "  \"role\": \"admin\",\n" +
                                                 "  \"active\": true\n" +
                                                 "}"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void createUserShouldReturnBadRequestIfFirstNameIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"  \",\n" +
                                                "  \"lastName\": \"Sashkin\",\n" +
                                                "  \"email\": \"max@email.com\",\n" +
                                                "  \"password\": \"pass123\",\n" +
                                                "  \"role\": \"admin\"\n" +
                                                "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void createUserShouldReturnBadRequestIfLastNameIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"Maxim\",\n" +
                                                "  \"lastName\": \" \",\n" +
                                                "  \"email\": \"max@email.com\",\n" +
                                                "  \"password\": \"pass123\",\n" +
                                                "  \"role\": \"admin\"\n" +
                                                "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void createUserShouldReturnBadRequestIfPasswordIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"Maxim\",\n" +
                                                "  \"lastName\": \"Sashkin\",\n" +
                                                "  \"email\": \"max@email.com\",\n" +
                                                "  \"password\": \" \",\n" +
                                                "  \"role\": \"admin\"\n" +
                                                "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void createUserShouldReturnBadRequestIfRoleIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"Maxim\",\n" +
                                                "  \"lastName\": \"Sashkin\",\n" +
                                                "  \"email\": \"max@email.com\",\n" +
                                                "  \"password\": \"pass123\",\n" +
                                                "  \"role\": \" \"\n" +
                                                "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void createUserShouldReturnBadRequestIfEmailIsNotValid() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"Maxim\",\n" +
                                                "  \"lastName\": \"Sashkin\",\n" +
                                                "  \"email\": \"wrongemail\",\n" +
                                                "  \"password\": \"pass123\",\n" +
                                                "  \"role\": \"admin\"\n" +
                                                "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getUsersIfUsersExist() throws Exception {
        when(getUsersQuery.getAllUsers()).thenReturn(List.of(User.withId(1L,
                                                                         "First name 1",
                                                                         "Last name 1",
                                                                         "one@email.com",
                                                                         "pass1",
                                                                         UserRole.ADMIN,
                                                                         true),
                                                             User.withId(2L,
                                                                         "First name 2",
                                                                         "Last name 2",
                                                                         "two@email.com",
                                                                         "pass2",
                                                                         UserRole.USER,
                                                                         false)));

        mockMvc.perform(get("/users/"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": true\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": false\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getUsersPagedIfUsersExist() throws Exception {
        when(getUsersQuery.getAllUsers(1, 2)).thenReturn(List.of(User.withId(1L,
                                                                             "First name 1",
                                                                             "Last name 1",
                                                                             "one@email.com",
                                                                             "pass1",
                                                                             UserRole.ADMIN,
                                                                             true),
                                                                 User.withId(2L,
                                                                             "First name 2",
                                                                             "Last name 2",
                                                                             "two@email.com",
                                                                             "pass2",
                                                                             UserRole.USER,
                                                                             false)));

        mockMvc.perform(get("/users?page=1&size=2"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": true\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": false\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getUserIfUserExists() throws Exception {
        when(getUsersQuery.getUserById(new User.UserId(666L)))
                .thenReturn(Optional.of(User.withId(666L,
                                                    "John",
                                                    "Johnovich",
                                                    "john@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    true)));

        mockMvc.perform(get("/users/666"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "{\n" +
                               "  \"id\": 666,\n" +
                               "  \"firstName\": \"John\",\n" +
                               "  \"lastName\": \"Johnovich\",\n" +
                               "  \"email\": \"john@email.com\",\n" +
                               "  \"role\": \"user\",\n" +
                               "  \"active\": true\n" +
                               "}"));
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
                .thenReturn(Optional.of(User.withId(888L,
                                                    "New First Name",
                                                    "New Last Name",
                                                    "new@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    true)));

        mockMvc.perform(put("/users/888")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"New First Name\",\n" +
                                                "  \"lastName\": \"New Last Name\",\n" +
                                                "  \"email\": \"new@email.com\"\n" +
                                                "}"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "{\n" +
                               "  \"firstName\": \"New First Name" +
                               "\",\n" +
                               "  \"lastName\": \"New Last Name\",\n" +
                               "  \"email\": \"new@email.com\",\n" +
                               "  \"role\": \"user\",\n" +
                               "  \"active\": true\n" +
                               "}"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void updateUserIfUserDoesNotExist() throws Exception {
        when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(888L),
                                                                                  "New First Name",
                                                                                  "New Last Name",
                                                                                  "new@email.com")))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/users/888")
                                .header("Content-Type", "application/json")
                                .content(
                                        "{\n" +
                                                "  \"firstName\": \"New First Name\",\n" +
                                                "  \"lastName\": \"New Last Name\",\n" +
                                                "  \"email\": \"new@email.com\"\n" +
                                                "}"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void activateUserIfUserExists() throws Exception {
        when(activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L))))
                .thenReturn(Optional.of(User.withId(123L,
                                                    "Max",
                                                    "Sashkin",
                                                    "maxs@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    true)));

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
                .thenReturn(Optional.of(User.withId(567L,
                                                    "Max",
                                                    "Sashkin",
                                                    "maxs@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    false)));

        mockMvc.perform(put("/users/deactivate/567"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void deactivateUserIfUserDoesNotExist() throws Exception {
        when(deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(567L))))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/users/deactivate/567"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getActiveUsersOnly() throws Exception {
        when(getUsersQuery.getAllUsersByActive(true)).thenReturn(List.of(User.withId(1L,
                                                                                     "First name 1",
                                                                                     "Last name 1",
                                                                                     "one@email.com",
                                                                                     "pass1",
                                                                                     UserRole.ADMIN,
                                                                                     true),
                                                                         User.withId(2L,
                                                                                     "First name 2",
                                                                                     "Last name 2",
                                                                                     "two@email.com",
                                                                                     "pass2",
                                                                                     UserRole.USER,
                                                                                     true)));

        mockMvc.perform(get("/users?active=true"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": true\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": true\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getActiveUsersOnlyPaged() throws Exception {
        when(getUsersQuery.getAllUsersByActive(true, 1, 2)).thenReturn(List.of(User.withId(1L,
                                                                                           "First name 1",
                                                                                           "Last name 1",
                                                                                           "one@email.com",
                                                                                           "pass1",
                                                                                           UserRole.ADMIN,
                                                                                           true),
                                                                               User.withId(2L,
                                                                                           "First name 2",
                                                                                           "Last name 2",
                                                                                           "two@email.com",
                                                                                           "pass2",
                                                                                           UserRole.USER,
                                                                                           true)));

        mockMvc.perform(get("/users?active=true&page=1&size=2"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": true\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": true\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getInactiveUsersOnly() throws Exception {
        when(getUsersQuery.getAllUsersByActive(false)).thenReturn(List.of(User.withId(1L,
                                                                                      "First name 1",
                                                                                      "Last name 1",
                                                                                      "one@email.com",
                                                                                      "pass1",
                                                                                      UserRole.ADMIN,
                                                                                      false),
                                                                          User.withId(2L,
                                                                                      "First name 2",
                                                                                      "Last name 2",
                                                                                      "two@email.com",
                                                                                      "pass2",
                                                                                      UserRole.USER,
                                                                                      false)));

        mockMvc.perform(get("/users?active=false"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": false\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": false\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void getInactiveUsersOnlyPaged() throws Exception {
        when(getUsersQuery.getAllUsersByActive(false, 1, 2)).thenReturn(List.of(User.withId(1L,
                                                                                            "First name 1",
                                                                                            "Last name 1",
                                                                                            "one@email.com",
                                                                                            "pass1",
                                                                                            UserRole.ADMIN,
                                                                                            false),
                                                                                User.withId(2L,
                                                                                            "First name 2",
                                                                                            "Last name 2",
                                                                                            "two@email.com",
                                                                                            "pass2",
                                                                                            UserRole.USER,
                                                                                            false)));

        mockMvc.perform(get("/users?active=false&page=1&size=2"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"active\": false\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"active\": false\n" +
                               "  }\n" +
                               "]"));
    }
}
