package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.dto.PhonelineDto;

import java.util.List;

public class PhonelineTestUtils {

    public static Phoneline getPhoneline () {
        City city = CityTestUtils.getCity();
        return Phoneline.builder()
                .id(1)
                .number("2235429130")
                .status(true)
                .city(city)
                .build();
    }

    public static PhonelineDto getPhonelineDto () {
        CityDto cityDto = CityTestUtils.getCityDto();
        return PhonelineDto.builder()
                .id(1)
                .number("2235429130")
                .status(true)
                .city(cityDto)
                .build();
    }

    public static Phoneline getPhonelineReceived () {
        return Phoneline.builder()
                .number("2235429130")
                .build();
    }

    public static PhonelineDto getPhonelineDtoReceived() {
        return PhonelineDto.builder()
                .number("2235429130")
                .build();
    }

    public static List<Phoneline> getPhonelineList () {
        Phoneline phoneline1 = getPhoneline();
        Phoneline phoneline2 = getPhoneline();
        phoneline2.setId(2);
        phoneline2.setNumber("2236632935");
        return List.of(phoneline1, phoneline2);
    }

    public static List <PhonelineDto> getPhonelineDtoList () {
        PhonelineDto phonelineDto1 = getPhonelineDto();
        PhonelineDto phonelineDto2 = getPhonelineDto();
        phonelineDto2.setId(2);
        phonelineDto2.setNumber("2236632935");
        return List.of(phonelineDto1, phonelineDto2);
    }
}
