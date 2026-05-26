package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DTO.UserDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Service API")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserControllerHTTP {

    UserService service;

    @Autowired
    public UserControllerHTTP(UserService servise) {
        this.service = servise;
    }

    @Operation(
            summary = "Create a new User in the Data Base",
            description = "Gets a UserDto, creates a new user in the database, returns a UserDto with an ID",
            parameters = {
                    @Parameter(
                            name = "user",
                            description = "Interface for working with the program's backend"
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {

        log.trace("Запуск метода createUser в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
    }

    @Operation(
            summary = "Returns all UserDto from the database",
            description = "Returns all UserDto from the database"
    )
    @GetMapping()
    public ResponseEntity<List<UserDto>> allUsers() {
        log.trace("Запуск метода allUsers в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.allUsers());

    }

    @Operation(
            summary = "Returns the UserDTO from the database by ID",
            description = "Gets ID, returns the UserDTO from the database by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id")Long id) {

        log.trace("Запуск метода getUserById в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));

    }

    @Operation(
            summary = "Updating the UserDTO by ID",
            description = "Gets ID and update data User, updating the UserDTO by ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") Long id, @RequestBody @Valid UserDto user) {

        log.trace("Запуск метода updateUserById в UserControllerHTTP");
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUserById(id, user));

    }
    @Operation(
            summary = "Delete the UserDTO by ID",
            description = "Gets ID User, delete the UserDTO by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {

        log.trace("Запуск метода deleteUserById в UserControllerHTTP");
        service.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
