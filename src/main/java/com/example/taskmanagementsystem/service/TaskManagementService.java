package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.model.Task;

import java.util.List;

public interface TaskManagementService {
    List<Task> findAllTasks();
    Task findTaskById(Long id);
    Task createTask(Task task);
    List<Task> findTasksByAuthors(String email);
    void deleteById(Long id);
    Task updatePriority(Long id, String newPriority);
    Task updateStatus(Long id, String newStatus);
}

