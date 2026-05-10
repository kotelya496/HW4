package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.DTO.UserDto;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.service.MapperUser.*;

@Slf4j
@Service
public class UserServise {

    UserRepository repository;

    @Autowired
    public UserServise(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto creatUser(UserDto user) {

        log.trace("Запуск метода createUser в UserServise");
        var userEntity = toUserEntity(user);
        return toDomainUser(repository.save(userEntity));
    }

    public List<UserDto> allUsers() {

        log.trace("Запуск метода allUsers в UserServise");
        List<UserEntity> allUserEtity = repository.findAll();
        return allUserEtity.stream().map(MapperUser::toDomainUser).toList();
    }

    public UserDto getUserById(Long id) {

        log.trace("Запуск метода getUserById в UserServise");
        var userEntity = repository.findById(id).orElseThrow(()-> {
            log.error("Не найден User по ID: {}", id);
            return new EntityNotFoundException("Не найден User по ID: " + id);
        });
        return toDomainUser(userEntity);
    }

    public UserDto updateUserById(Long id, UserDto user) {

        log.trace("Запуск метода updateUserById в UserServise");
        var userEntity = repository.findById(id).orElseThrow(()-> {
            log.error("Не найден User по ID: {}", id);
            return new EntityNotFoundException("Не найден User по ID: " + id);
        });
        var updateUser = repository.save(mapUpdateEntity(userEntity,user));
        return toDomainUser(updateUser);
    }

    public void deleteUserById(Long id) {

        log.trace("Запуск метода deleteUserById в UserServise");
        if (!repository.existsById(id)){
            log.error("Не найден User по ID: {}", id);
            throw new  EntityNotFoundException("Не найден User по ID: " + id);
        }
        repository.deleteById(id);
    }
}
