package org.example.service;

import org.example.controller.UserControllerHTTP;
import org.example.service.DTO.UserDto;
import org.example.service.DTO.UserHateoasDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserHateoasDto> {

    public UserModelAssembler() {
        super(UserControllerHTTP.class, UserHateoasDto.class);
    }

    @Override
    public UserHateoasDto toModel(UserDto user) {
        UserHateoasDto model = UserHateoasDto.fromUserDto(user);

        model.add(linkTo(methodOn(UserControllerHTTP.class).getUserById(user.id())).withSelfRel());

        model.add(linkTo(methodOn(UserControllerHTTP.class).allUsers()).withRel("all-users"));

        model.add(linkTo(methodOn(UserControllerHTTP.class).updateUserById(user.id(), null))
                .withRel("update"));

        model.add(linkTo(methodOn(UserControllerHTTP.class).deleteUserById(user.id()))
                .withRel("delete"));

        return model;
    }
}
