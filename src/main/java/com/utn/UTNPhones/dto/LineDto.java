package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineDto {
    private Integer id;
    private String number;
    private Boolean status;
    private CityDto city;

    public static LineDto from (Line line) {
        return LineDto.builder()
                .id(line.getId())
                .number(line.getNumber())
                .status(line.getStatus())
                .city(CityDto.from(line.getCity()))
                .build();
    }
}
