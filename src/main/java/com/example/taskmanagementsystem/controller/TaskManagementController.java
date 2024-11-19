package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.domain.model.Task;
import com.example.taskmanagementsystem.service.TaskManagementService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskManagementController {
    private final TaskManagementService taskManagementService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findTaskById(@PathVariable Long id){
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.findTaskById(id)), HttpStatus.OK);
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<?> findTasksByAuthor(@PathVariable String email){
//        return new ResponseEntity<>(new Gson().toJson(taskManagementService.findTasksByAuthors(email)), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> findAllTask() {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.findAllTasks()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(new Gson().toJson(taskManagementService.updateStatus(id, status)), HttpStatus.OK);
    }
}
