package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.dto.BackofficeDto;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.exceptions.BackofficeExistsException;
import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.LineBadDataException;
import com.utn.UTNPhones.exceptions.LineNoExistsException;
import com.utn.UTNPhones.service.backoffice.BackofficeService;
import com.utn.UTNPhones.service.backoffice.ClientService;
import com.utn.UTNPhones.service.backoffice.PersonService;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice")
public class PersonController {

    private final PersonService personService;
    private final ClientService clientService;
    private final BackofficeService backofficeService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ClientService clientService, BackofficeService backofficeService, ModelMapper modelMapper) {
        this.personService = personService;
        this.clientService = clientService;
        this.backofficeService = backofficeService;
        this.modelMapper = modelMapper;
    }

    /*///// Save new client/backoffice with your new user /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addClient(@RequestBody @Validated final PersonDto personDto)
            throws ClientExistsException, LineNoExistsException, LineBadDataException, BackofficeExistsException {
        *//*LineDto lineDto = clientDto.getLine();
        if (lineDto == null)
            throw new LineRequiredException();
        if ((lineDto.getId() == null) && (lineDto.getNumber() == null))
            throw new LineBadDataException();*//*
        *//*if (personDto.getClass().equals(Client.class)) {
            Client newClient = clientService.addClient(modelMapper.map(personDto, Client.class));
            //        userService.addUser(newClient);
            return ResponseEntity.created(Conf.getLocation(newClient)).build();
        }
        else{
            Backoffice newBackoffice = backofficeService.addBackoffice(modelMapper.map(personDto, Backoffice.class));
            return ResponseEntity.created(Conf.getLocation(newBackoffice)).build();
        }*//*
        Person newperson = personService.addPerson(modelMapper.map(personDto, Person.class));
        //        userService.addUser(newClient);
        return ResponseEntity.created(Conf.getLocation(newperson)).build();

    }*/

    ///// Save new client with your new user /////
    @PostMapping(path = "/clients/", consumes = "application/json")
    public ResponseEntity addClient(@RequestBody @Validated final ClientDto clientDto)
            throws ClientExistsException, LineNoExistsException, LineBadDataException {
        /*LineDto lineDto = clientDto.getLine();
        if (lineDto == null)
            throw new LineRequiredException();
        if ((lineDto.getId() == null) && (lineDto.getNumber() == null))
            throw new LineBadDataException();*/
        Client newClient = clientService.addClient(modelMapper.map(clientDto, Client.class));
//        userService.addUser(newClient);
        return ResponseEntity.created(Conf.getLocation(newClient)).build();
    }

    ///// Get all Clients /////
    @GetMapping(path = "/clients/", produces = "application/json")
    public ResponseEntity<List<ClientDto>> allClients(Pageable pageable) {
        Page<Client> page = clientService.getAll(pageable);
        return Conf.response(page);
    }


    ///// Save new backoffice /////
    @PostMapping(path = "/backoffices/", consumes = "application/json")
    public ResponseEntity addBackoffice(@RequestBody @Validated final BackofficeDto backofficeDto)
            throws BackofficeExistsException {
        Backoffice newBackoffice = backofficeService.addBackoffice(modelMapper.map(backofficeDto, Backoffice.class));
        return ResponseEntity.created(Conf.getLocation(newBackoffice)).build();
    }

    ///// Get all Backoffices /////
    @GetMapping(path = "/backoffices/", produces = "application/json")
    public ResponseEntity<List<BackofficeDto>> allBackoffice(Pageable pageable) {
        Page<Backoffice> page = backofficeService.getAll(pageable);
        return Conf.response(page);
    }
}