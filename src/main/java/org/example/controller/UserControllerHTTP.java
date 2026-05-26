package org.example.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DTO.UserDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserControllerHTTP {

    UserService service;

    @Autowired
    public UserControllerHTTP(UserService servise) {
        this.service = servise;
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {

        log.trace("Запуск метода createUser в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> allUsers() {
        log.trace("Запуск метода allUsers в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.allUsers());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id")Long id) {

        log.trace("Запуск метода getUserById в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") Long id, @RequestBody @Valid UserDto user) {

        log.trace("Запуск метода updateUserById в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUserById(id, user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {

        log.trace("Запуск метода deleteUserById в UserControllerHTTP");
        service.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
