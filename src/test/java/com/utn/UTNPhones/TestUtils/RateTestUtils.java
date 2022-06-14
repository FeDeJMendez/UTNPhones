package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Rate;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.dto.RateDto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RateTestUtils {

    public static Rate getRate () {
        City origin = CityTestUtils.getCityList().get(0);
        City destination = CityTestUtils.getCityList().get(1);
        return Rate.builder()
                .id(1)
                .price(5.00)
                .starttime(LocalTime.of(12,00,00))
                .endtime(LocalTime.of(12,05,00))
                .origin(origin)
                .destination(destination)
                .build();
    }

    public static RateDto getRateDto() {
        CityDto originDto = CityTestUtils.getCityDtoList().get(0);
        CityDto destinationDto = CityTestUtils.getCityDtoList().get(1);
        return RateDto.builder()
                .id(1)
                .price(5.00)
                .starttime(LocalTime.of(12,00,00))
                .endtime(LocalTime.of(12,05,00))
                .origin(originDto)
                .destination(destinationDto)
                .build();
    }

    public static Rate getRateReceived () {
        City origin = City.builder()
                .id(1)
                .build();
        City destination = City.builder()
                .id(2)
                .build();
        return Rate.builder()
                .id(1)
                .price(5.00)
                .starttime(LocalTime.of(12,00,00))
                .endtime(LocalTime.of(12,05,00))
                .origin(origin)
                .destination(destination)
                .build();
    }

    public static RateDto getRateDtoReceived() {
        CityDto originDto = CityDto.builder()
                .id(1)
                .build();
        CityDto destinationDto = CityDto.builder()
                .id(2)
                .build();
        return RateDto.builder()
                .id(1)
                .price(5.00)
                .starttime(LocalTime.of(12,00,00))
                .endtime(LocalTime.of(12,05,00))
                .origin(originDto)
                .destination(destinationDto)
                .build();
    }

    public static List<Rate> getRateList () {
        Rate rate1 = getRate();
        Rate rate2 = getRate();
        rate2.setId(2);
        rate2.setStarttime(LocalTime.of(12,10,00));
        rate2.setEndtime(LocalTime.of(12,20,00));
        return List.of(rate1, rate2);
    }

    public static List<RateDto> getRateDtoList () {
        RateDto rateDto1 = getRateDto();
        RateDto rateDto2 = getRateDto();
        rateDto2.setId(2);
        rateDto2.setStarttime(LocalTime.of(12,10,00));
        rateDto2.setEndtime(LocalTime.of(12,20,00));
        return List.of(rateDto1, rateDto2);
    }

}
