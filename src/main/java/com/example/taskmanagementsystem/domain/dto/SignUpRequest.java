package com.example.taskmanagementsystem.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    @Email(message = "Email should be valid")
    private String name;
    @Size(min = 5, max = 20, message
            = "About Me must be between 5 and 20 characters")
    @NotNull
    private String password;
}