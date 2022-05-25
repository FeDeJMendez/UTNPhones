package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.utn.UTNPhones.domain.Backoffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("BACKOFFICE")
public class BackofficeDto extends PersonDto {

    public static BackofficeDto from (Backoffice backoffice) {
        BackofficeDto backofficeDto = new BackofficeDto();
        backofficeDto.setId(backoffice.getId());
        backofficeDto.setName(backoffice.getName());
        backofficeDto.setLastname(backoffice.getLastname());
        backofficeDto.setDni(backoffice.getDni());
        return backofficeDto;
    }
}
