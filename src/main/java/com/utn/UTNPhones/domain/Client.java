package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.dto.ClientDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("CLIENT")
@EqualsAndHashCode(callSuper = true)
@Entity
//@Table(name = "clients")
public class Client extends Person {
    @OneToOne
    @JoinColumn(name = "phoneline_id ", unique = true)
    private Phoneline phoneline;

    public static Client from (ClientDto clientDto) {
        /*Phoneline phoneline = Phoneline.builder()
                .id(clientDto.getPhoneline().getId())
                .number(clientDto.getPhoneline().getNumber())
                .build();*/
        Client client = new Client();
//        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setLastname(clientDto.getLastname());
        client.setDni(clientDto.getDni());
//        client.setPhoneline(phoneline);
        return client;
    }
}
