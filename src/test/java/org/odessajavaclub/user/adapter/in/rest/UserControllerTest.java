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
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void testCreateUserShouldReturnOkIfUserIsOk() throws Exception {
        when(createUserUseCase.createActivatedUser(new CreateUserUseCase.CreateUserCommand("Maxim",
                                                                                           "Sashkin",
                                                                                           "max@email.com",
                                                                                           "pass123",
                                                                                           UserRole.ADMIN)))
                .thenReturn(User.withId(new User.UserId(1L),
                                        "Maxim",
                                        "Sashkin",
                                        "max@email.com",
                                        "pass123",
                                        UserRole.ADMIN,
                                        false));

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
                                                 "  \"deactivated\": false\n" +
                                                 "}"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfFirstNameIsBlank() throws Exception {
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
    void testCreateUserShouldReturnBadRequestIfLastNameIsBlank() throws Exception {
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
    void testCreateUserShouldReturnBadRequestIfPasswordIsBlank() throws Exception {
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
    void testCreateUserShouldReturnBadRequestIfRoleIsBlank() throws Exception {
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
    void testCreateUserShouldReturnBadRequestIfEmailIsNotValid() throws Exception {
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
    void testGetUsersIfUsersExist() throws Exception {
        when(getUsersQuery.getUsers()).thenReturn(List.of(User.withId(new User.UserId(1L),
                                                                      "First name 1",
                                                                      "Last name 1",
                                                                      "one@email.com",
                                                                      "pass1",
                                                                      UserRole.ADMIN,
                                                                      false),
                                                          User.withId(new User.UserId(2L),
                                                                      "First name 2",
                                                                      "Last name 2",
                                                                      "two@email.com",
                                                                      "pass2",
                                                                      UserRole.USER,
                                                                      true)));

        mockMvc.perform(get("/users/")
                                .header("Content-Type", "application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "[\n" +
                               "  {\n" +
                               "    \"id\": 1,\n" +
                               "    \"firstName\": \"First name 1\",\n" +
                               "    \"lastName\": \"Last name 1\",\n" +
                               "    \"email\": \"one@email.com\",\n" +
                               "    \"role\": \"admin\",\n" +
                               "    \"deactivated\": false\n" +
                               "  " +
                               "},\n" +
                               "  {\n" +
                               "    \"id\": 2,\n" +
                               "    \"firstName\": \"First name 2\",\n" +
                               "    \"lastName\": \"Last name 2\",\n" +
                               "    \"email\": \"two@email.com\",\n" +
                               "    \"role\": \"user\",\n" +
                               "    \"deactivated\": true\n" +
                               "  }\n" +
                               "]"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testGetUserIfUserExists() throws Exception {
        when(getUsersQuery.getUser(new GetUsersQuery.UserQuery(new User.UserId(666L))))
                .thenReturn(Optional.of(User.withId(new User.UserId(666L),
                                                    "John",
                                                    "Johnovich",
                                                    "john@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    false)));

        mockMvc.perform(get("/users/666")
                                .header("Content-Type", "application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(
                       "{\n" +
                               "  \"id\": 666,\n" +
                               "  \"firstName\": \"John\",\n" +
                               "  \"lastName\": \"Johnovich\",\n" +
                               "  \"email\": \"john@email.com\",\n" +
                               "  \"role\": \"user\",\n" +
                               "  \"deactivated\": false\n" +
                               "}"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testGetUserIfUserDoesNotExist() throws Exception {
        when(getUsersQuery.getUser(new GetUsersQuery.UserQuery(new User.UserId(666L))))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/users/666")
                                .header("Content-Type", "application/json"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testDeleteUserIfUserExists() throws Exception {
        when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(777L))))
                .thenReturn(true);

        mockMvc.perform(delete("/users/777")
                                .header("Content-Type", "application/json"))
               .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testDeleteUserIfUserDoesNotExist() throws Exception {
        when(deleteUserUseCase.deleteUser(new DeleteUserUseCase.DeleteUserCommand(new User.UserId(777L))))
                .thenReturn(false);

        mockMvc.perform(delete("/users/777")
                                .header("Content-Type", "application/json"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testUpdateUserIfUserExists() throws Exception {
        when(updateUserUseCase.updateUser(new UpdateUserUseCase.UpdateUserCommand(new User.UserId(888L),
                                                                                  "New First Name",
                                                                                  "New Last Name",
                                                                                  "new@email.com")))
                .thenReturn(Optional.of(User.withId(new User.UserId(888L),
                                                    "New First Name",
                                                    "New Last Name",
                                                    "new@email.com",
                                                    "pass1",
                                                    UserRole.USER,
                                                    false)));

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
                               "  \"deactivated\": false\n" +
                               "}"));
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testUpdateUserIfUserDoesNotExist() throws Exception {
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
    void testActivateUserIfUserExists() throws Exception {
        when(activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L))))
                .thenReturn(true);

        mockMvc.perform(put("/users/activate/123"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testActivateUserIfUserDoesNotExist() throws Exception {
        when(activateUserUseCase.activateUser(new ActivateUserUseCase.ActivateUserCommand(new User.UserId(123L))))
                .thenReturn(false);

        mockMvc.perform(put("/users/activate/123"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testDeactivateUserIfUserExists() throws Exception {
        when(deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(567L))))
                .thenReturn(true);

        mockMvc.perform(put("/users/deactivate/567"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testDeactivateUserIfUserDoesNotExist() throws Exception {
        when(deactivateUserUseCase.deactivateUser(new DeactivateUserUseCase.DeactivateUserCommand(new User.UserId(567L))))
                .thenReturn(false);

        mockMvc.perform(put("/users/deactivate/567"))
               .andExpect(status().isNotFound());
    }
}
