package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Person;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.dto.BackofficeDto;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.dto.PersonDto;
import com.utn.UTNPhones.dto.PhonelineDto;

import java.util.List;

public class PersonTestUtils {

    public static Client getClientReceived () {
        Phoneline phoneline = Phoneline.builder()
                .id(1)
                .number("2235429130")
                .build();
        Client client = new Client();
        client.setName("Federico");
        client.setLastname("Mendez");
        client.setDni(34058251);
        client.setPhoneline(phoneline);
        return client;
    }

    public static ClientDto getClientDtoReceived () {
        PhonelineDto phonelineDto = PhonelineDto.builder()
                .id(1)
                .number("2235429130")
                .build();
        ClientDto clientDto = new ClientDto();
        clientDto.setName("Federico");
        clientDto.setLastname("Mendez");
        clientDto.setDni(34058251);
        clientDto.setPhoneline(phonelineDto);
        return clientDto;
    }

    public static Client getClient () {
        Phoneline phoneline = Phoneline.builder()
                .id(1)
                .number("2235429130")
                .build();
        Client client = new Client();
        client.setId(1);
        client.setName("Federico");
        client.setLastname("Mendez");
        client.setDni(34058251);
        client.setPhoneline(phoneline);
        return client;
    }

    public static ClientDto getClientDto () {
        PhonelineDto phonelineDto = PhonelineDto.builder()
                .id(1)
                .number("2235429130")
                .build();
        ClientDto clientDto = new ClientDto();
        clientDto.setId(1);
        clientDto.setName("Federico");
        clientDto.setLastname("Mendez");
        clientDto.setDni(34058251);
        clientDto.setPhoneline(phonelineDto);
        return clientDto;
    }

    public static List<Client> getClientList () {
        Client client1 = getClient();
        Client client2 = getClient();
        Phoneline phoneline2 = Phoneline.builder()
                .id(2)
                .number("2236632935")
                .build();
        client2.setPhoneline(phoneline2);
        client2.setId(2);
        client2.setName("Eva");
        client2.setLastname("Gomez");
        client2.setDni(5702404);
        return List.of(client1, client2);
    }

    public static List<ClientDto> getClientDtoList () {
        ClientDto clientDto1 = getClientDto();
        ClientDto clientDto2 = getClientDto();
        PhonelineDto phonelineDto2 = PhonelineDto.builder()
                .id(2)
                .number("2236632935")
                .build();
        clientDto2.setPhoneline(phonelineDto2);
        clientDto2.setId(2);
        clientDto2.setName("Eva");
        clientDto2.setLastname("Gomez");
        clientDto2.setDni(5702404);
        return List.of(clientDto1, clientDto2);
    }



    public static Backoffice getBackofficeReceived () {
        Backoffice backoffice = new Backoffice();
        backoffice.setName("Federico");
        backoffice.setLastname("Mendez");
        backoffice.setDni(34058251);
        return backoffice;
    }

    public static BackofficeDto getBackofficeDtoReceived () {
        BackofficeDto backofficeDto = new BackofficeDto();
        backofficeDto.setName("Federico");
        backofficeDto.setLastname("Mendez");
        backofficeDto.setDni(34058251);
        return backofficeDto;
    }

    public static Backoffice getBackoffice () {
        Backoffice backoffice = new Backoffice();
        backoffice.setId(1);
        backoffice.setName("Federico");
        backoffice.setLastname("Mendez");
        backoffice.setDni(34058251);
        return backoffice;
    }

    public static BackofficeDto getBackofficeDto () {
        BackofficeDto backofficeDto = new BackofficeDto();
        backofficeDto.setId(1);
        backofficeDto.setName("Federico");
        backofficeDto.setLastname("Mendez");
        backofficeDto.setDni(34058251);
        return backofficeDto;
    }

    public static List<Backoffice> getBackofficeList () {
        Backoffice backoffice1 = getBackoffice();
        Backoffice backoffice2 = getBackoffice();
        backoffice2.setId(2);
        backoffice2.setName("Eva");
        backoffice2.setLastname("Gomez");
        backoffice2.setDni(5702404);
        return List.of(backoffice1, backoffice2);
    }

    public static List<BackofficeDto> getBackofficeDtoList () {
        BackofficeDto backofficeDto1 = getBackofficeDto();
        BackofficeDto backofficeDto2 = getBackofficeDto();
        backofficeDto2.setId(2);
        backofficeDto2.setName("Eva");
        backofficeDto2.setLastname("Gomez");
        backofficeDto2.setDni(5702404);
        return List.of(backofficeDto1, backofficeDto2);
    }


}
