package com.utn.UTNPhones.dto;

import com.utn.UTNPhones.domain.User;
import com.utn.UTNPhones.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Integer id;
    String username;
//    String password;
    Rol rol;
    Integer person_id;

    public static  UserDto from (User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
//                .password(user.getPassword())
                .rol(user.getRol())
                .person_id(user.getPerson_id())
                .build();
    }
}
