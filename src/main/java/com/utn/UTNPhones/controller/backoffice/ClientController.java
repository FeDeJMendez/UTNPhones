package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.LineBadDataException;
import com.utn.UTNPhones.exceptions.LineNoExistsException;
import com.utn.UTNPhones.service.backoffice.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/clients")
public class ClientController {

    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    ///// Save new client with your new user /////
    @PostMapping(path = "/", consumes = "application/json")
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
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ClientDto>> allClients(Pageable pageable) {
        Page<Client> page = clientService.getAll(pageable);
        return Conf.Response(page);
    }
}
