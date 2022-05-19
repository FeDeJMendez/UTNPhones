package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Line;
import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.LineBadDataException;
import com.utn.UTNPhones.exceptions.LineNoExistsException;
import com.utn.UTNPhones.exceptions.LineRequiredException;
import com.utn.UTNPhones.repository.ClientRepository;
import com.utn.UTNPhones.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final LineRepository lineRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, LineRepository lineRepository) {
        this.clientRepository = clientRepository;
        this.lineRepository = lineRepository;
    }

    public Client addClient(Client client)
            throws ClientExistsException, LineNoExistsException, LineBadDataException {
        Line line = client.getLine();
        if (clientRepository.existsByDni(client.getDni()))
            throw new ClientExistsException();
        if (line != null){
            Line newLine = new Line();
            if(line.getId() != null)
                newLine = lineRepository.findById(line.getId())
                        .orElseThrow(LineNoExistsException::new);
            else if (line.getNumber() != null)
                newLine = lineRepository.findByNumber(line.getNumber())
                        .orElseThrow(LineNoExistsException::new);
            else throw new LineBadDataException();
            client.setLine(newLine);
        }
        return (Client)clientRepository.save(client);
    }

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
}
