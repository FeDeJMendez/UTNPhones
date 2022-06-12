package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.dto.BackofficeDto;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.UserService;
import com.utn.UTNPhones.service.roles.BackofficeService;
import com.utn.UTNPhones.service.roles.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice")
public class PersonController {

    private final ClientService clientService;
    private final BackofficeService backofficeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(ClientService clientService, BackofficeService backofficeService, UserService userService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.backofficeService = backofficeService;
        this.userService = userService;
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

    ///// ///// CLIENTS ///// /////

    ///// Save new client /////
    @PostMapping(path = "/clients/", consumes = "application/json")
    public ResponseEntity addClient(@RequestBody @Validated final ClientDto clientDto)
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException {
        /*LineDto lineDto = clientDto.getLine();
        if (lineDto == null)
            throw new LineRequiredException();
        if ((lineDto.getId() == null) && (lineDto.getNumber() == null))
            throw new LineBadDataException();*/
        Client newClient = clientService.addClient(modelMapper.map(clientDto, Client.class));
        userService.addUser(newClient);
        return ResponseEntity.created(Conf.getLocation(newClient)).build();
    }

    ///// Get all Clients /////
    @GetMapping(path = "/clients/", produces = "application/json")
    public ResponseEntity<List<ClientDto>> allClients(Pageable pageable) {
        Page<Client> page = clientService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Delete Client By Dni /////
    @DeleteMapping(value = "/clients/{dni}", produces = "application/json")
    public ResponseEntity deleteClientByDni(@PathVariable(value = "dni") Integer dni)
            throws ClientNotExistsException {
        clientService.deleteByDni(dni);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    ///// Edit Client /////
    @PutMapping(path = "clients/{dni}", produces = "application/json")
    public ResponseEntity editClient(@RequestBody ClientDto clientDto, @PathVariable Integer dni)
            throws ClientNotExistsException {
        Client editedClient = clientService.editClient(modelMapper.map(clientDto, Client.class), dni);
        return  ResponseEntity.ok().build();
    }

    ///// Assign Line to Client/////
    @PutMapping(path = "/clients/{idClient}/lines/{number}", produces = "application/json")
    public ResponseEntity assignLineToClient(@PathVariable Integer idClient, @PathVariable String number)
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        clientService.assignLineToClient(idClient, number);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    ///// Delete Line to Client /////
    @PutMapping(path = "/clients/{idClient}/notline", produces = "application/json")
    public ResponseEntity deleteLineToClient(@PathVariable Integer idClient)
            throws ClientNotExistsException {
        clientService.deleteLineToClient(idClient);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    ///// ///// BACKOFFICE ///// /////

    ///// Save new backoffice /////
    @PostMapping(path = "/backoffices/", consumes = "application/json")
    public ResponseEntity addBackoffice(@RequestBody @Validated final BackofficeDto backofficeDto)
            throws BackofficeExistsException {
        Backoffice newBackoffice = backofficeService.addBackoffice(modelMapper.map(backofficeDto, Backoffice.class));
        userService.addUser(newBackoffice);
        return ResponseEntity.created(Conf.getLocation(newBackoffice)).build();
    }

    ///// Get all Backoffices /////
    @GetMapping(path = "/backoffices/", produces = "application/json")
    public ResponseEntity<List<BackofficeDto>> allBackoffice(Pageable pageable) {
        Page<Backoffice> page = backofficeService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Delete Backoffice By Dni /////
    @DeleteMapping(value = "/backoffices/{dni}", produces = "application/json")
    public ResponseEntity deleteBackofficeByDni(@PathVariable(value = "dni") Integer dni)
            throws BackofficeNotExistsException {
        backofficeService.deleteByDni(dni);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
