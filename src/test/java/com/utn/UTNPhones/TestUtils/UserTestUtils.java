package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.domain.User;
import com.utn.UTNPhones.dto.UserDto;

import static com.utn.UTNPhones.domain.enums.Rol.ROLE_BACKOFFICE;

public class UserTestUtils {

    public static User getUser(){
        return User.builder()
                .id(1)
                .username("client1")
                .password("1234")
                .rol(ROLE_BACKOFFICE)
                .person_id(null).
                build();
    }

    public static UserDto getUserDto(){
        return UserDto.builder().
                id(1).
                username("backoffice1").
                rol(ROLE_BACKOFFICE).
                person_id(null).
                build();
    }
}
