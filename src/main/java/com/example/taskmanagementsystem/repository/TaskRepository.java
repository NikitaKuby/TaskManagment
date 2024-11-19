package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findTaskById(Long id);
    boolean existsByEmailAuthorOfTheTask(String email);
    List<Task> findTaskByEmailAuthorOfTheTask(String email);

}
