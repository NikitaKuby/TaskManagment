package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.dto.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.dto.SignInRequest;
import com.example.taskmanagementsystem.domain.dto.SignUpRequest;
import com.example.taskmanagementsystem.domain.model.Role;
import com.example.taskmanagementsystem.domain.model.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Getter
    private String email;
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = UserDetailsImpl.builder()
                .username(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        email = user.getUsername();
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getName());

        var jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        return new JwtAuthenticationResponse(jwt);
    }

}
