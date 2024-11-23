package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.domain.dto.CommentsDto;
import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.model.TaskComments;
import com.example.taskmanagementsystem.service.TaskManagementService;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskManagementController {
    private final TaskManagementService taskManagementService;

    @GetMapping("/{email}")
    public ResponseEntity<?> findTaskById(@PathVariable String email){
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.findTasksByAuthors(email)), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> findAllTask() {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.findAllTasks()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto task) {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.createTask(task)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskManagementService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<?> updateTaskPriority(@PathVariable Long id, @RequestParam String priority) {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.updatePriority(id, priority)), HttpStatus.OK);
    }

    //USER ROLE
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.updateStatus(id, status)), HttpStatus.OK);
    }

    @PatchMapping("/{id}/assignExecutor")
    public ResponseEntity<?> updateExecutorById(@PathVariable Long id, @RequestParam String email) {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.updateExecutorById(id, email)), HttpStatus.OK);
    }

    @PostMapping("/{taskId}/comments")
    public TaskComments createComment(@PathVariable (value = "taskId") Long taskId,
                                      @RequestBody @Valid CommentsDto comment) {
        return taskManagementService.createCommentByTaskId(taskId, comment);
    }

}
