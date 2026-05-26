package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DTO.UserDto;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.event.MessageEvent;
import org.example.service.event.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.service.MapperUser.*;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    @Autowired
    public UserService(UserRepository repository, KafkaTemplate<String, MessageEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserDto createUser(UserDto user) {

        log.trace("Запуск метода createUser в UserService");
        var userEntity = toUserEntity(user);
        var existUser = toDomainUser(repository.save(userEntity));
        var messageEvent = new MessageEvent(existUser.id(), existUser.email(), StatusMessage.CREATED_USER);
        kafkaTemplate.send("message-topic", messageEvent);
        return existUser;
    }

    public List<UserDto> allUsers() {

        log.trace("Запуск метода allUsers в UserService");
        List<UserEntity> allUserEntity = repository.findAll();
        return allUserEntity.stream().map(MapperUser::toDomainUser).toList();
    }

    public UserDto getUserById(Long id) {

        log.trace("Запуск метода getUserById в UserService");
        var userEntity = repository.findById(id).orElseThrow(()-> {
            log.error("Не найден User по ID: {}", id);
            return new EntityNotFoundException("Не найден User по ID: " + id);
        });
        return toDomainUser(userEntity);
    }

    public UserDto updateUserById(Long id, UserDto user) {

        log.trace("Запуск метода updateUserById в UserService");
        var userEntity = repository.findById(id).orElseThrow(()-> {
            log.error("Не найден User по ID: {}", id);
            return new EntityNotFoundException("Не найден User по ID: " + id);
        });
        var updateUser = repository.save(mapUpdateEntity(userEntity,user));
        return toDomainUser(updateUser);
    }

    public void deleteUserById(Long id) {

        log.trace("Запуск метода deleteUserById в UserService");
        var existUser = repository.findById(id).orElseThrow(()-> {
            log.error("Не найден User по ID: {}", id);
            return new EntityNotFoundException("Не найден User по ID: " + id);
        });
        var messageEvent = new MessageEvent(existUser.getId(), existUser.getEmail(), StatusMessage.DELETE_USER);
        kafkaTemplate.send("message-topic",messageEvent);
        repository.deleteById(id);
    }
}
