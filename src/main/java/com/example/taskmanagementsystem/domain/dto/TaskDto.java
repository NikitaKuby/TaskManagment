package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.domain.model.PriorityTask;
import com.example.taskmanagementsystem.domain.model.StatusTask;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    @NotNull(message = "title required")
    private String title;
    @Size(max = 4048, message = "The description exceeds the allowed size")
    private String description;
    @Email(message = "Email should be valid")
    private String emailAuthorOfTheTask;
    private String taskPerformer;
    private StatusTask statusTask;
    private PriorityTask priorityTask;
}
