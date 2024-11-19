package com.example.taskmanagementsystem.controller;


import com.example.taskmanagementsystem.domain.dto.ExceptionAnswerDTO;
import com.example.taskmanagementsystem.exceptions.InvalidFormatDataException;
import com.example.taskmanagementsystem.exceptions.TaskIdNotFoundException;
import com.example.taskmanagementsystem.exceptions.UserNotFoundException;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(InvalidFormatDataException.class)
    public ResponseEntity<?> handleNoDataFormatError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionAnswerDTO("Invalid format data")),headers,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskIdNotFoundException.class)
    public ResponseEntity<?> idNotFount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionAnswerDTO("Task not found or Id entered incorrectly")),headers,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionAnswerDTO("User not found")),headers,HttpStatus.BAD_REQUEST);
    }




}