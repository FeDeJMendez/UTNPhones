package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
    private Integer id;
    private String name;
    private String lastname;
    private Integer dni;
    private LineDto line;

    public static ClientDto from (Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .lastname(client.getLastname())
                .dni(client.getDni())
                .line(LineDto.from(client.getLine()))
                .build();
    }
}
