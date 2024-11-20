package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.model.Task;
import com.example.taskmanagementsystem.domain.model.TaskComments;

import java.util.List;

public interface TaskManagementService {
    List<Task> findAllTasks();
    Task findTaskById(Long id);
    Task createTask(TaskDto task);
    List<Task> findTasksByAuthors(String email);
    void deleteById(Long id);
    Task updatePriority(Long id, String newPriority);
    Task updateStatus(Long id, String newStatus);
    Task updateExecutorById(Long id,String email);
    TaskComments createCommentByTaskId(Long taskId, TaskComments comment);

}

