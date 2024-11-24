package com.example.taskmanagementsystem.service.impl;

import com.example.taskmanagementsystem.domain.dto.CommentsDto;
import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.model.PriorityTask;
import com.example.taskmanagementsystem.domain.model.StatusTask;
import com.example.taskmanagementsystem.domain.model.Task;
import com.example.taskmanagementsystem.domain.model.TaskComments;
import com.example.taskmanagementsystem.exceptions.*;
import com.example.taskmanagementsystem.repository.CommentsRepository;
import com.example.taskmanagementsystem.repository.TaskRepository;
import com.example.taskmanagementsystem.service.TaskManagementService;
import com.example.taskmanagementsystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {
    private final TaskRepository taskRepository;
    private final CommentsRepository commentsRepository;
    private final UserService userService;

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findTaskById(id)
                .orElseThrow(TaskIdNotFoundException::new);
    }

    @Override
    @Transactional
    public Task createTask(TaskDto task){
        Task newTask = Task.builder()
                .emailAuthorOfTheTask(userService.getCurrentUser().getUsername())
                .priorityTask(task.getPriorityTask())
                .statusTask(task.getStatusTask())
                .taskPerformer(task.getTaskPerformer())
                .description(task.getDescription())
                .title(task.getTitle())
                .build();
        return taskRepository.save(newTask);
    }

    @Override
    @Transactional
    public List<Task> findTasksByAuthors(String email) {
        if (!taskRepository.existsByEmailAuthorOfTheTask(email)) {
            throw new UserNotFoundException();
        }
        return taskRepository.findTaskByEmailAuthorOfTheTask(email);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (taskRepository.existsById(id)){
           if(userService.doesTheUserHavePermission(id)){
            taskRepository.deleteById(id);
            }else throw new UserWithoutAccessException();
        }else throw new TaskIdNotFoundException();


    }

    @Override
    @Transactional
    public Task updatePriority(Long id, String newPriority) {
        if(userService.doesTheUserHavePermission(id)){
            try {
                Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
                oldTask.setPriorityTask(PriorityTask.valueOf(newPriority.toUpperCase()));
                return taskRepository.save(oldTask);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatDataException();
            }
        }else{
            throw new UserWithoutAccessException();
        }
    }

    @Override
    @Transactional
    public Task updateStatus(Long id, String newStatus) {
        if(userService.doesTheUserHavePermission(id)||userService.doesTheUserHavePermissionExecutor(id)){
            try {
                Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
                oldTask.setStatusTask(StatusTask.valueOf(newStatus.toUpperCase()));
                return taskRepository.save(oldTask);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatDataException();
            }
        }else{
            throw new UserWithoutAccessException();
        }
    }

    @Override
    @Transactional
    public Task updateExecutorById(Long id, String email) {
        if(userService.doesTheUserHavePermission(id)) {
            Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
            if(userService.existUserByUsername(email)) {
                oldTask.setTaskPerformer(email);
                return taskRepository.save(oldTask);
            }else throw new EmailExecutorNotFoundException();
        }else {
            throw new UserWithoutAccessException();
        }
    }

    @Override
    @Transactional
    public TaskComments createCommentByTaskId(Long taskId, CommentsDto comment) {
        if(userService.doesTheUserHavePermission(taskId)|| userService.doesTheUserHavePermissionExecutor(taskId)) {
            Task task = taskRepository.findTaskById(taskId).orElseThrow(TaskIdNotFoundException::new);
            TaskComments newComments = TaskComments.builder()
                    .comment(comment.getComment())
                    .postId(task.getId())
                    .emailCommentators(userService.getCurrentUser().getUsername())
                    .build();
            return commentsRepository.save(newComments);
        }else {
            throw new UserWithoutAccessException();
        }
    }

    public void getAdmin(){
        userService.getAdmin();
    }

}
