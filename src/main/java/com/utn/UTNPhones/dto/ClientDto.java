package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.utn.UTNPhones.domain.Client;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("CLIENT")
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends PersonDto{
    private PhonelineDto phonelineDto;

    public static ClientDto from (Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setLastname(client.getLastname());
        clientDto.setDni(client.getDni());
        clientDto.setPhonelineDto(PhonelineDto.from(client.getPhoneline()));
        return clientDto;
    }
}
