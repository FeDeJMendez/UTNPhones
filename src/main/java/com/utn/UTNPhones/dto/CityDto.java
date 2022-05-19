package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDto {
    private Integer id;
    private String name;
    private Integer prefix;
    private ProvinceDto province;
    private List<LineDto> lines;

    public static CityDto from (City city) {
        List<Line> lines = city.getLines();
        List<LineDto> linesDto = null;
        if (lines != null)
            linesDto = lines.stream().map(x -> LineDto.from(x)).collect(Collectors.toList());
        return CityDto.builder().
                id(city.getId()).
                name(city.getName()).
                prefix(city.getPrefix()).
                province(ProvinceDto.from(city.getProvince())).
                lines(linesDto).
                build();
    }
}
