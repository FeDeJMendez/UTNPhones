package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Province;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.dto.ProvinceDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CityTestUtils {
    private static ModelMapper modelMapper;

    public static City getCity () {
        return City.builder()
                .id(1)
                .name("Mar del Plata")
                .prefix(223)
                .province(Province.builder()
                        .id(1)
                        .name("Buenos Aires")
                        .build())
                .build();
    }

    public static CityDto getCityDto () {
        return CityDto.from(getCity());
    }

    public static CityDto getCityDtoReceived () {
        ProvinceDto provinceDto = ProvinceDto.builder()
                .id(1)
                .build();
        return CityDto.builder()
                .name("Mar del Plata")
                .prefix(223)
                .province(provinceDto)
                .build();
    }

    public static City getCityReceived () {
        Province province = Province.builder()
                .id(1)
                .build();
        return City.builder()
                .name("Mar del Plata")
                .prefix(223)
                .province(province)
                .build();
    }

    public static List<City> getCityList () {
        Province province = Province.builder()
                .id(1)
                .name("Buenos Aires")
                .build();
        return List.of(
                City.builder()
                        .id(1)
                        .name("Mar del Plata")
                        .prefix(223)
                        .province(province)
                        .build(),
                City.builder()
                        .id(2)
                        .name("Balcarce")
                        .prefix(2266)
                        .province(province)
                        .build()
        );
    }

    public static List<CityDto> getCityDtoList () {
        return getCityList().stream().
                map(x -> CityDto.from(x)).
                collect(Collectors.toList());

    }
}
