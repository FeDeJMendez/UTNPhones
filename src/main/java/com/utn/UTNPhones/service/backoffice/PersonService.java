package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Person;
import com.utn.UTNPhones.exceptions.BackofficeExistsException;
import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.PhonelineBadDataException;
import com.utn.UTNPhones.exceptions.PhonelineNoExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final ClientService clientService;
    private final BackofficeService backofficeService;

    @Autowired
    public PersonService(ClientService clientService, BackofficeService backofficeService) {
        this.clientService = clientService;
        this.backofficeService = backofficeService;
    }

    public Person addPerson(Person newPerson)
            throws ClientExistsException, PhonelineBadDataException, PhonelineNoExistsException, BackofficeExistsException {
        if (newPerson.getClass().equals(Client.class))
            return clientService.addClient((Client) newPerson);
        else
            return backofficeService.addBackoffice((Backoffice) newPerson);
    }
}
