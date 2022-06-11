package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.domain.Rate;
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
//    private List<PhonelineDto> lines;
//    private List<RateDto> origins;
//    private List<RateDto> destinations;

    public static CityDto from (City city) {

        /*List<Phoneline> phonelines = city.getPhonelines();
        List<PhonelineDto> phonelinesDto = null;
        if (phonelines != null)
            phonelinesDto = phonelines.stream().map(x -> PhonelineDto.from(x)).collect(Collectors.toList());

        List<Rate> origins = city.getOrigins();
        List<RateDto> originsDto = null;
        if (origins != null)
            originsDto = origins.stream().map(x -> RateDto.from(x)).collect(Collectors.toList());

        List<Rate> destinations = city.getDestinations();
        List<RateDto> destinationsDto = null;
        if (destinations != null)
            destinationsDto = destinations.stream().map(x -> RateDto.from(x)).collect(Collectors.toList());*/

        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .prefix(city.getPrefix())
                .province(ProvinceDto.from(city.getProvince()))
//                .lines(phonelinesDto)
//                .origins(originsDto)
//                .destinations(destinationsDto)
                .build();
    }
}
