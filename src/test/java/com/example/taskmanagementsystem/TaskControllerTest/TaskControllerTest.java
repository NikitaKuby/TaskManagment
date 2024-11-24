package com.example.taskmanagementsystem.TaskControllerTest;

import com.example.taskmanagementsystem.controller.TaskManagementController;
import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.model.PriorityTask;
import com.example.taskmanagementsystem.domain.model.StatusTask;
import com.example.taskmanagementsystem.domain.model.Task;
import com.example.taskmanagementsystem.service.TaskManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class TaskControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private TaskManagementController taskController;
    private ObjectMapper objectMapper;
    @Mock
    private TaskManagementService taskManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTask() throws Exception {
        TaskDto task = TaskDto.builder()
                .emailAuthorOfTheTask("admin@mail.ru")
                .title("First")
                .build();
        Task taskRequest=Task.builder()
                .id(1L)
                .emailAuthorOfTheTask("admin@mail.ru")
                .title("First")
                .build();
        String workerJson = objectMapper.writeValueAsString(task);
        when(taskManagementService.createTask(task)).thenReturn(taskRequest);
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workerJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(new Gson().toJson(taskRequest)));
    }

    @Test
    void deleteTask() throws Exception {
        Long taskId = 1L;

        doNothing().when(taskManagementService).deleteById(taskId);

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        verify(taskManagementService, times(1)).deleteById(taskId);
    }

    @Test
    void updateTaskPriority() throws Exception {
        Long taskId = 1L;
        PriorityTask priorityTask=PriorityTask.HIGH;
        Task updatedTask = Task.builder()
                .id(taskId)
                .emailAuthorOfTheTask("admin@mail.ru")
                .title("task title")
                .priorityTask(priorityTask)
                .build();

        when(taskManagementService.updatePriority(taskId, priorityTask.name())).thenReturn(updatedTask);

        mockMvc.perform(patch("/tasks/{id}/priority", taskId)
                        .param("priority", priorityTask.name()))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(updatedTask)));

        verify(taskManagementService, times(1)).updatePriority(taskId, priorityTask.name());
    }

    @Test
    void updateTaskStatus() throws Exception {
        Long taskId = 1L;
        String newStatus = "COMPLETED";
        Task updatedTask =Task.builder()
                .id(taskId)
                .emailAuthorOfTheTask("admin@mail.ru")
                .title("task title")
                .statusTask(StatusTask.valueOf(newStatus))
                .build();
        when(taskManagementService.updateStatus(taskId, newStatus)).thenReturn(updatedTask);

        mockMvc.perform(patch("/tasks/{id}/status", taskId)
                        .param("status", newStatus))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(updatedTask)));

        verify(taskManagementService, times(1)).updateStatus(taskId, newStatus);
    }

    @Test
    void updateExecutorById() throws Exception {
        Long taskId = 1L;
        String executorEmail = "executor@mail.com";
        Task updatedTask = Task.builder()
                .id(taskId)
                .taskPerformer(executorEmail)
                .title("task title")
                .build();
        when(taskManagementService.updateExecutorById(taskId, executorEmail)).thenReturn(updatedTask);

        mockMvc.perform(patch("/tasks/{id}/assignExecutor", taskId)
                        .param("email", executorEmail))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(updatedTask)));

        verify(taskManagementService, times(1)).updateExecutorById(taskId, executorEmail);
    }
}
