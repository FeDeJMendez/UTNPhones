package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.domain.Province;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.dto.PhonelineDto;

import java.time.LocalDateTime;
import java.util.List;

public class CallTestUtils {

    public static List<Call> getCallList () {
        Province province = Province.builder()
                .id(1)
                .name("Buenos Aires")
                .build();
        Phoneline origin = Phoneline.builder()
                .id(1)
                .number("2235429130")
                .status(true)
                .city(City.builder()
                        .id(1)
                        .name("Mar del Plata")
                        .prefix(223)
                        .province(province)
                        .build())
                .build();
        Phoneline destination = Phoneline.builder()
                .id(2)
                .number("2236632935")
                .status(true)
                .city(City.builder()
                        .id(1)
                        .name("Mar del Plata")
                        .prefix(223)
                        .province(province)
                        .build())
                .build();

        return List.of(
                Call.builder()
                        .id(1)
                        .starttime(LocalDateTime.of(2022,03,10,00,00,00))
                        .duration(5)
                        .total(35.00)
                        .idBill(0)
                        .origin(origin)
                        .destination(destination)
                        .build(),
                Call.builder()
                        .id(2)
                        .starttime(LocalDateTime.of(2022,03,11,00,00,00))
                        .duration(6)
                        .total(42.00)
                        .idBill(0)
                        .origin(origin)
                        .destination(destination)
                        .build()
        );
    }

    public static List<CallDto> getCallDtoList(){
        return Conf.listCallsToDto(getCallList());
    }

}
