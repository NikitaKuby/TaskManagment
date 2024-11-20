package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.domain.model.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findByUsername(String username);
    boolean existsByUsername(String username);
}