package org.example.service.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserHateoasDto extends RepresentationModel<UserHateoasDto> {
    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime createdat;

    public static UserHateoasDto fromUserDto(UserDto userDto) {
        UserHateoasDto userHateoasDto = new UserHateoasDto();
        userHateoasDto.setId(userDto.id());
        userHateoasDto.setName(userDto.name());
        userHateoasDto.setEmail(userDto.email());
        userHateoasDto.setAge(userDto.age());
        userHateoasDto.setCreatedat(userDto.createdat());
        return userHateoasDto;
    }
}
