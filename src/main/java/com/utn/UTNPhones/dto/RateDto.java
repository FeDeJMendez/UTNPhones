package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Rate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateDto {
    private Integer id;
    private Double price;
    private LocalTime starttime;
    private LocalTime endtime;
    private CityDto origin;
    private CityDto destination;

    public static RateDto from (Rate rate) {
        return RateDto.builder()
                .id(rate.getId())
                .price(rate.getPrice())
                .starttime(rate.getStarttime())
                .endtime(rate.getEndtime())
                .origin(CityDto.from(rate.getOrigin()))
                .destination(CityDto.from(rate.getDestination()))
                .build();
    }
}
