package com.example.taskmanagementsystem.service.impl;

import com.example.taskmanagementsystem.domain.dto.TaskDto;
import com.example.taskmanagementsystem.domain.model.PriorityTask;
import com.example.taskmanagementsystem.domain.model.StatusTask;
import com.example.taskmanagementsystem.domain.model.Task;
import com.example.taskmanagementsystem.domain.model.TaskComments;
import com.example.taskmanagementsystem.exceptions.InvalidFormatDataException;
import com.example.taskmanagementsystem.exceptions.TaskIdNotFoundException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.example.taskmanagementsystem.repository.CommentsRepository;
import com.example.taskmanagementsystem.repository.TaskRepository;
import com.example.taskmanagementsystem.service.TaskManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {
    private final TaskRepository taskRepository;
    private final CommentsRepository commentsRepository;

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(Long id) {
        //todo Exception
        return taskRepository.findTaskById(id)
                .orElseThrow(TaskIdNotFoundException::new);
    }

    @Override
    @Transactional
    public Task createTask(TaskDto task){
        Task newTask = Task.builder()
                .emailAuthorOfTheTask(Math.random()*100000+"@mail.ru")
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
        if (!taskRepository.existsById(id)) {
            throw new TaskIdNotFoundException();
        }else{
            taskRepository.deleteById(id);
        }
    }

    @Override
    public Task updatePriority(Long id, String newPriority) {
        try {
            Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
            oldTask.setPriorityTask(PriorityTask.valueOf(newPriority.toUpperCase()));
            return taskRepository.save(oldTask);
        }catch(IllegalArgumentException e){
            throw new InvalidFormatDataException();
        }
    }

    @Override
    public Task updateStatus(Long id, String newStatus) {
        try {
            Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
            oldTask.setStatusTask(StatusTask.valueOf(newStatus.toUpperCase()));
            return taskRepository.save(oldTask);
        }catch(IllegalArgumentException e){
            throw new InvalidFormatDataException();
        }
    }

    @Override
    public Task updateExecutorById(Long id, String email) {
        try {
            Task oldTask = taskRepository.findTaskById(id).orElseThrow(TaskIdNotFoundException::new);
            oldTask.setTaskPerformer(email);
            return taskRepository.save(oldTask);
        }catch(IllegalArgumentException e){
            throw new InvalidFormatDataException();
        }
    }

    @Override
    public TaskComments createCommentByTaskId(Long taskId, TaskComments comment) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(TaskIdNotFoundException::new);
        TaskComments newComments = TaskComments.builder()
                .comment(comment.getComment())
                .postId(task.getId())
                .emailCommentators("EMAIL>RU")
                .build();
        return commentsRepository.save(newComments);
    }

}
