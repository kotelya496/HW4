package org.example.service;


import org.example.service.DTO.UserDto;
import org.example.entity.UserEntity;

public class MapperUser {

    public static UserEntity toUserEntity(UserDto user){
        return new UserEntity(user.name(), user.email(), user.age());
    }

    public static UserEntity mapUpdateEntity(UserEntity userEntity, UserDto user){

        userEntity.setName(user.name());
        userEntity.setEmail(user.email());
        userEntity.setAge(user.age());
        return userEntity;
    }

    public static UserDto toDomainUser(UserEntity userEntity){

        return new UserDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getAge(),
                userEntity.getCreatedat()
        );
    }
}
