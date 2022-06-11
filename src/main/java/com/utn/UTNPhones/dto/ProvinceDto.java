package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceDto {
    private Integer id;
    private String name;
//    private List<CityDto> cities;

    public static ProvinceDto from (Province province) {
        /*List<City> cities = province.getCities();
        List<CityDto> citiesDto = null;
        if (cities != null)
            citiesDto = cities.stream().map(x -> CityDto.from(x)).collect(Collectors.toList());*/
        return ProvinceDto.builder().
                id(province.getId()).
                name(province.getName()).
//                cities(citiesDto).
                build();
    }
}
