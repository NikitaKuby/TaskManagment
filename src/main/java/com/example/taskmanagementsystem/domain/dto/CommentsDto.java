package com.example.taskmanagementsystem.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
@Data
public class CommentsDto {
    @NotNull
    @Size(max = 1024, message = "The comment exceeds the allowed size")
    String comment;
}
