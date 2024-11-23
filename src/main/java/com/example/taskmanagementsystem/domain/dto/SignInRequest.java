package com.example.taskmanagementsystem.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
    @NotNull
    @Email(message = "Email should be valid")
    private String name;
    @Size(min = 10, max = 20, message
            = "About Me must be between 10 and 20 characters")
    @NotNull
    private String password;
}
