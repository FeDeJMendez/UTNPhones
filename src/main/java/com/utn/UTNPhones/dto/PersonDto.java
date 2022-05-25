package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Person;
import com.utn.UTNPhones.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientDto.class, name = "CLIENT"),
        @JsonSubTypes.Type(value = BackofficeDto.class, name = "BACKOFFICE")
})
public class PersonDto {
    Integer id;
    String name;
    String lastname;
    Integer dni;

    /*public static PersonDto from (Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .lastname(person.getLastname())
                .dni(person.getDni())
                .build();
    }*/
}
