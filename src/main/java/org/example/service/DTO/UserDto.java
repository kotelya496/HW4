package org.example.service.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

@Schema(description = "Сущность пользователя")
public record UserDto(

        @Schema(description = "Id User")
        @Null
        Long id,
        @Schema(description = "Name User")
        @NotNull
        String name,
        @Schema(description = "Email User")
        @NotNull
        @Email
        String email,
        @NotNull
        @Schema(description = "Age User")
        int age,
        @Null
        @Schema(description = "Time created User in database")
        LocalDateTime createdat
) { }
