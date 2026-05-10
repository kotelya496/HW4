package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.controller.DTO.UserDto;
import org.example.service.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

@Slf4j
@Controller
public class UserControllerConsole {

    UserServise servise;

    @Autowired
    public UserControllerConsole(UserServise userServise) {
        this.servise = userServise;
    }

    public UserDto createUser(UserDto user) {

        return servise.creatUser(user);

    }

    public List<UserDto> allUsers() {

        return servise.allUsers();
    }

    public UserDto getUserById(Long id) {

        return servise.getUserById(id);
    }

    public UserDto updateUserById(Long id, UserDto user) {

        return servise.updateUserById(id, user);
    }

    public void deleteUserById(Long id) {

        servise.deleteUserById(id);
    }
}
