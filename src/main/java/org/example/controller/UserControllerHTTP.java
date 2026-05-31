package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DTO.UserDto;
import org.example.service.DTO.UserHateoasDto;
import org.example.service.UserModelAssembler;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Service API")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserControllerHTTP {

    private final UserService service;
    private final UserModelAssembler assembler;

    @Autowired
    public UserControllerHTTP(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
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
    public ResponseEntity<UserHateoasDto> createUser(@RequestBody @Valid UserDto user) {

        log.trace("Запуск метода createUser в UserControllerHTTP");

//        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
        UserDto createdUser = service.createUser(user);
        UserHateoasDto hateoasUser = assembler.toModel(createdUser);

        hateoasUser.add(linkTo(methodOn(UserControllerHTTP.class).createUser(null)).withRel("create"));

        return ResponseEntity.status(HttpStatus.CREATED).body(hateoasUser);
    }

    @Operation(
            summary = "Returns all UserDto from the database",
            description = "Returns all UserDto from the database"
    )
    @GetMapping()
    public ResponseEntity<CollectionModel<UserHateoasDto>> allUsers() {
        log.trace("Запуск метода allUsers в UserControllerHTTP");

        List<UserDto> users = service.allUsers();

        List<UserHateoasDto> hateoasUsers = users.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(UserControllerHTTP.class).allUsers()).withSelfRel();
        Link createLink = linkTo(methodOn(UserControllerHTTP.class).createUser(null)).withRel("create");

        CollectionModel<UserHateoasDto> collectionModel = CollectionModel.of(hateoasUsers, selfLink, createLink);

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @Operation(
            summary = "Returns the UserDTO from the database by ID",
            description = "Gets ID, returns the UserDTO from the database by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserHateoasDto> getUserById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable("id")Long id) {

        log.trace("Запуск метода getUserById в UserControllerHTTP");
//        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));

        UserDto user = service.getUserById(id);
        UserHateoasDto hateoasUser = assembler.toModel(user);

        return ResponseEntity.status(HttpStatus.OK).body(hateoasUser);
    }

    @Operation(
            summary = "Updating the UserDTO by ID",
            description = "Gets ID and update data User, updating the UserDTO by ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserHateoasDto> updateUserById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable("id") Long id,
            @RequestBody @Valid UserDto user) {

        log.trace("Запуск метода updateUserById в UserControllerHTTP");
//        return ResponseEntity.status(HttpStatus.OK).body(service.updateUserById(id, user));
        UserDto updatedUser = service.updateUserById(id, user);
        UserHateoasDto hateoasUser = assembler.toModel(updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(hateoasUser);
    }
    @Operation(
            summary = "Delete the UserDTO by ID",
            description = "Gets ID User, delete the UserDTO by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable("id") Long id) {

        log.trace("Запуск метода deleteUserById в UserControllerHTTP");
        service.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
