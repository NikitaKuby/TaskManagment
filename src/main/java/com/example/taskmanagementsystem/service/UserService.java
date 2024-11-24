package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.model.Role;
import com.example.taskmanagementsystem.domain.model.UserDetailsImpl;
import com.example.taskmanagementsystem.exceptions.UserExistException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.example.taskmanagementsystem.repository.TaskRepository;
import com.example.taskmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final TaskRepository taskRepository;
    public UserDetailsImpl save(UserDetailsImpl user) {
        return repository.save(user);
    }

    public UserDetailsImpl create(UserDetailsImpl user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserExistException();
        }
        return save(user);
    }

    public UserDetailsImpl getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserDetailsImpl getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public boolean doesTheUserHavePermission(Long id){
        String userEmail = getCurrentUser().getUsername();
        String userTaskEmail=taskRepository.findTaskById(id).get().getEmailAuthorOfTheTask();
        if(getCurrentUser().getRole().equals(Role.USER)&&userEmail.equals(userTaskEmail)){
            return true;
        }else
            return getCurrentUser().getRole().equals(Role.ADMIN);
    }

    public boolean doesTheUserHavePermissionExecutor(Long id){
        String userEmail = getCurrentUser().getUsername();
        String userTaskEmail=taskRepository.findTaskById(id).get().getTaskPerformer();
        if(getCurrentUser().getRole().equals(Role.USER)&&userEmail.equals(userTaskEmail)){
            return true;
        }else
            return getCurrentUser().getRole().equals(Role.ADMIN);
    }

    public boolean existUserByUsername(String email){
        return repository.existsByUsername(email);
    }

    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ADMIN);
        save(user);
    }
}
