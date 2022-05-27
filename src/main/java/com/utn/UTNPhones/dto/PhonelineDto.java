package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.Phoneline;
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
public class PhonelineDto {
    private Integer id;
    private String number;
    private Boolean status;
    private CityDto city;
    private List<CallDto> origins;
    private List<CallDto> destinations;

    public static PhonelineDto from (Phoneline phoneline) {
        List<Call> origins = phoneline.getOrigins();
        List<CallDto> originsDto = null;
        if (origins != null)
            originsDto = origins.stream().map(x -> CallDto.from(x)).collect(Collectors.toList());

        List<Call> destinations = phoneline.getDestinations();
        List<CallDto> destinationsDto = null;
        if (destinations != null)
            destinationsDto = destinations.stream().map(x -> CallDto.from(x)).collect(Collectors.toList());

        return PhonelineDto.builder()
                .id(phoneline.getId())
                .number(phoneline.getNumber())
                .status(phoneline.getStatus())
                .city(CityDto.from(phoneline.getCity()))
                .origins(originsDto)
                .destinations(destinationsDto)
                .build();
    }
}
