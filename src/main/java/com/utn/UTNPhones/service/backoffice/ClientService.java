package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.*;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PhonelineService phonelineService;

    @Autowired
    public ClientService(ClientRepository clientRepository, PhonelineService lineService) {
        this.clientRepository = clientRepository;
        this.phonelineService = lineService;
    }

    public Client addClient(Client newClient)
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException {
        Phoneline phoneline = newClient.getPhoneline();
        if (clientRepository.existsByDni(newClient.getDni()))
            throw new ClientExistsException();
        if (phoneline != null){
            Phoneline newPhoneline = new Phoneline();
            if(phoneline.getId() != null)
                newPhoneline = phonelineService.getById(phoneline.getId());
            else if (phoneline.getNumber() != null)
                newPhoneline = phonelineService.getByNumber(phoneline.getNumber());
            else throw new PhonelineBadDataException();
            newClient.setPhoneline(newPhoneline);
        }
        return clientRepository.save(newClient);
    }

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getById (Integer id)
            throws ClientNotExistsException {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotExistsException::new);
    }

    public Client getByDni (Integer dni)
            throws ClientNotExistsException {
        return clientRepository.findByDni(dni)
                .orElseThrow(ClientNotExistsException::new);
    }

    public Boolean existsById (Integer id) {
        return clientRepository.existsById(id);
    }

    public void deleteByDni(Integer dni)
            throws ClientNotExistsException {
        Person person = this.getByDni(dni);
        clientRepository.deleteById(person.getId());
    }

    public Client editClient(Client client, Integer dni)
            throws ClientNotExistsException {
        Client editedClient = this.getByDni(dni);

        editedClient.setName(client.getName());
        editedClient.setLastname(client.getLastname());
        editedClient.setDni(client.getDni());

        clientRepository.save(editedClient);
        return editedClient;

    }

    public void assignLineToClient(Integer idClient, String number)
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        Phoneline phoneline = phonelineService.getByNumber(number);
        Client clientCheck = clientRepository.findByIdPhoneline(phoneline.getId());
        if (clientCheck != null)
            throw new PhonelineAsignedException();
        Client client = this.getById(idClient);

        client.setPhoneline(phoneline);
        clientRepository.save(client);
    }

    public void deleteLineToClient (Integer idClient)
            throws ClientNotExistsException {
        Client client = this.getById(idClient);
        client.setPhoneline(null);
        clientRepository.save(client);
    }

}
