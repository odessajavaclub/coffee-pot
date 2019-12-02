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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"Maxim\", \"lastName\": \"Sashkin\", \"email\": \"max@email.com\", \"password\": \"pass123\", \"role\": \"admin\" }"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfFirstNameIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"  \", \"lastName\": \"Sashkin\", \"email\": \"max@email.com\", \"password\": \"pass123\", \"role\": \"admin\" }"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfLastNameIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"Maxim\", \"lastName\": \" \", \"email\": \"max@email.com\", \"password\": \"pass123\", \"role\": \"admin\" }"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfPasswordIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"Maxim\", \"lastName\": \"Sashkin\", \"email\": \"max@email.com\", \"password\": \" \", \"role\": \"admin\" }"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfRoleIsBlank() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"Maxim\", \"lastName\": \"Sashkin\", \"email\": \"max@email.com\", \"password\": \"pass123\", \"role\": \" \" }"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testCreateUserShouldReturnBadRequestIfEmailIsNotValid() throws Exception {
        mockMvc.perform(post("/users/")
                                .header("Content-Type", "application/json")
                                .content("{ \"firstName\": \"Maxim\", \"lastName\": \"Sashkin\", \"email\": \"wrongemail\", \"password\": \"pass123\", \"role\": \"admin\" }"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_NAME)
    void testGetUsers() throws Exception {
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
               .andExpect(content().json("[{\"id\":1,\"firstName\":\"First name 1\",\"lastName\":\"Last name 1\",\"role\":\"admin\"},{\"id\":2,\"firstName\":\"First name 2\",\"lastName\":\"Last name 2\",\"role\":\"user\"}]"));
    }

    @Test
    void testGetUser() {
    }

    @Test
    void testDeleteUser() {
    }

    @Test
    void testUpdateUser() {
    }

    @Test
    void testActivateUser() {
    }

    @Test
    void testDeactivate() {
    }
}