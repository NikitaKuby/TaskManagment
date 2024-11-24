package com.example.taskmanagementsystem.TaskControllerTest;

import com.example.taskmanagementsystem.controller.SecurityController;
import com.example.taskmanagementsystem.domain.dto.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.dto.SignInRequest;
import com.example.taskmanagementsystem.domain.dto.SignUpRequest;
import com.example.taskmanagementsystem.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SecurityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private SecurityController securityController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void signUp() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("user@example.com", "password");
        JwtAuthenticationResponse response = new JwtAuthenticationResponse("jwt-token");

        when(authenticationService.signUp(signUpRequest)).thenReturn(response);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(authenticationService, times(1)).signUp(signUpRequest);
    }

    @Test
    void signIn() throws Exception {
        SignInRequest signInRequest = new SignInRequest("user@example.com", "password");
        JwtAuthenticationResponse response = new JwtAuthenticationResponse("jwt-token");

        when(authenticationService.signIn(signInRequest)).thenReturn(response);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(authenticationService, times(1)).signIn(signInRequest);
    }
}