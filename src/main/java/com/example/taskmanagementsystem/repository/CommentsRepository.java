package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.domain.model.TaskComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<TaskComments,Long> {

}
