package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.ClientNoExistsException;
import com.utn.UTNPhones.exceptions.PhonelineBadDataException;
import com.utn.UTNPhones.exceptions.PhonelineNoExistsException;
import com.utn.UTNPhones.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PhonelineService lineService;

    @Autowired
    public ClientService(ClientRepository clientRepository, PhonelineService lineService) {
        this.clientRepository = clientRepository;
        this.lineService = lineService;
    }

    public Client addClient(Client newClient)
            throws ClientExistsException, PhonelineNoExistsException, PhonelineBadDataException {
        Phoneline phoneline = newClient.getPhoneline();
        if (clientRepository.existsByDni(newClient.getDni()))
            throw new ClientExistsException();
        if (phoneline != null){
            Phoneline newPhoneline = new Phoneline();
            if(phoneline.getId() != null)
                newPhoneline = lineService.getById(phoneline.getId());
            else if (phoneline.getNumber() != null)
                newPhoneline = lineService.getByNumber(phoneline.getNumber());
            else throw new PhonelineBadDataException();
            newClient.setPhoneline(newPhoneline);
        }
        return clientRepository.save(newClient);
    }

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getById (Integer id)
            throws ClientNoExistsException {
        return clientRepository.findById(id)
                .orElseThrow(ClientNoExistsException::new);
    }

    public Client getByDni (Integer dni)
            throws ClientNoExistsException {
        return clientRepository.findByDni(dni)
                .orElseThrow(ClientNoExistsException::new);
    }

    public Boolean existsById (Integer id) {
        return clientRepository.existsById(id);
    }

    public void deleteByDni(Integer dni)
            throws ClientNoExistsException {
        Client client = this.getByDni(dni);
        clientRepository.deleteById(client.getId());
    }
}
