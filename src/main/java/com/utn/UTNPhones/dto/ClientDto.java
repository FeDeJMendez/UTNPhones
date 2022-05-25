package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.utn.UTNPhones.domain.Client;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("CLIENT")
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends PersonDto{
    private LineDto line;

    public static ClientDto from (Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setLastname(client.getLastname());
        clientDto.setDni(client.getDni());
        clientDto.setLine(LineDto.from(client.getLine()));
        return clientDto;
    }
}
