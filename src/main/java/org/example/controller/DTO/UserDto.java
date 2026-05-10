package org.example.controller.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record UserDto(

        @Null
        Long id,
        @NotNull
        String name,
        @NotNull
        @Email
        String email,
        @NotNull
        int age,
        @Null
        LocalDateTime createdat
) { }
