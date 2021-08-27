package com.example.eshopweb.controller;

import com.example.eshopweb.exception.NotFoundException;
import com.example.eshopweb.pojo.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Message> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Message(e.getMessage()));
    }

}
