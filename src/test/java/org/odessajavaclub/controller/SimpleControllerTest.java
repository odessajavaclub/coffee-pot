package org.odessajavaclub.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odessajavaclub.auth.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {SimpleController.class})
public class SimpleControllerTest {

    private static final String USER_NAME = "Some random test user name";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationFacade authenticationFacade;

    @Before
    public void setUp() {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(USER_NAME, "some random pass", "USER");

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
    }

    @Test
    @WithMockUser(username = USER_NAME)
    public void testGetCurrentUserInsuredObjectsShouldExecuted() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }
}
