package com.example.taskmanagementsystem.domain.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CommentsDto {
    String email;
    String comment;
}
