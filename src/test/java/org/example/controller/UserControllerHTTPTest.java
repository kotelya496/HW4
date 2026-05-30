package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.service.DTO.UserDto;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerHTTPTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    UserService servise;

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {

        UserDto savedUser = createUserDtoforTest(1l,"Vasya","vasya@mail.ru",60);

        when(servise.createUser(any(UserDto.class))).thenReturn(savedUser);

        String jsonRequest = """
            {
                "name": "Vasya",
                "email": "vasya@mail.ru",
                "age": 60
            }
            """;

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("@.name").value("Vasya"))
                .andExpect(jsonPath("$.email").value("vasya@mail.ru"))
                .andExpect(jsonPath("$.age").value(60));

        verify(servise, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void createUser_givenIdiInBody_ShouldReturnExeptionBadRequest() throws Exception {

        String jsonRequest = """
            {
                "id": 5,
                "name": "Vasya",
                "email": "vasya@mail.ru",
                "age": 60
            }
            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createUser_givenInvalidEmailInBody_ShouldReturnExeptionBadRequest() throws Exception {

        String jsonRequest = """
            {
                "name": "Vasya",
                "email": "vasyamail.ru",
                "age": 60
            }
            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

    }

    @Test
    void allUsers_ShouldReturnStatusOkAndListUser() throws Exception {

        UserDto user1 = createUserDtoforTest(1l,"Vasya","vasya@mail.ru",60);
        UserDto user2 = createUserDtoforTest(2l,"Nasta","nasta@mail.ru",30);
        var listUser = List.of(user1,user2);

        when(servise.allUsers()).thenReturn(listUser);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$._embedded.users[0].name").value("Vasya"))
                .andExpect(jsonPath("$._embedded.users[1].name").value("Nasta"));

        verify(servise, times(1)).allUsers();
    }

    @Test
    void getUserById_ShouldReturnStatusOk() throws Exception {

        UserDto user = createUserDtoforTest(1l,"Vasya","vasya@mail.ru",60);

        when(servise.getUserById(1l)).thenReturn(user);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("$.name").value("Vasya"))
                .andExpect(jsonPath("$.email").value("vasya@mail.ru"));

        verify(servise, times(1)).getUserById(1l);
    }

    @Test
    void getUserById_GivenInvalidId_ShouldReturnExeptionNotFound() throws Exception {

        when(servise.getUserById(1l)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserById_ShouldReturnStatusOk() throws Exception {

        UserDto userInBase = createUserDtoforTest(1l,"Vasya","vasya@mail.ru",60);

        when(servise.updateUserById(eq(1l) , any(UserDto.class))).thenReturn(userInBase);

        String jsonRequest = """
            {
                "name": "Vasya",
                "email": "vasya@mail.ru",
                "age": 60
            }
            """;

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(servise, times(1)).updateUserById(eq(1l), any(UserDto.class));
    }

    @Test
    void updateUserById_GivenInvalidId_ShouldReturnExeptionNotFound() throws Exception {

        when(servise.updateUserById(eq(1l),any(UserDto.class))).thenThrow(new EntityNotFoundException());

        String jsonRequest = """
            {
                "name": "Vasya",
                "email": "vasya@mail.ru",
                "age": 60
            }
            """;

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());

        verify(servise, times(1)).updateUserById(eq(1l), any(UserDto.class));
    }

    @Test
    void deleteUserById_ShouldReturnStatusOk() throws Exception {

        doNothing().when(servise).deleteUserById(1L);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUserById_GivenInvalidId_ShouldReturnExeptionNotFound() throws Exception {

        doThrow(new EntityNotFoundException()).when(servise).deleteUserById(1L);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound());

    }

    private UserDto createUserDtoforTest(Long id, String name, String email, int age){

        var userDto = new UserDto(id,name,email,age, LocalDateTime.now());
        return userDto;
    }

}