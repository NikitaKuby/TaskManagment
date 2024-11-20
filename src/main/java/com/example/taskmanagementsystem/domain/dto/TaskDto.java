package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.domain.model.PriorityTask;
import com.example.taskmanagementsystem.domain.model.StatusTask;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    private String title;
    private String description;
    private String emailAuthorOfTheTask;
    private String taskPerformer;
    private StatusTask statusTask;
    private PriorityTask priorityTask;
}
