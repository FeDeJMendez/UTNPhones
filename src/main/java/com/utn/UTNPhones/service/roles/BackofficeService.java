package com.utn.UTNPhones.service.roles;

import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.exceptions.BackofficeExistsException;
import com.utn.UTNPhones.exceptions.BackofficeNotExistsException;
import com.utn.UTNPhones.repository.BackofficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class  BackofficeService {
    private final BackofficeRepository backofficeRepository;

    @Autowired
    public BackofficeService(BackofficeRepository backofficeRepository) {
        this.backofficeRepository = backofficeRepository;
    }


    public Backoffice addBackoffice(Backoffice newBackoffice)
            throws BackofficeExistsException {
        if (backofficeRepository.existsByDni(newBackoffice.getDni()))
            throw new BackofficeExistsException();
        return backofficeRepository.save(newBackoffice);
    }

    public Page<Backoffice> getAll(Pageable pageable) {
        return backofficeRepository.findAll(pageable);
    }

    private Backoffice getByDni(Integer dni)
            throws BackofficeNotExistsException {
        return backofficeRepository.findByDni(dni)
                .orElseThrow(BackofficeNotExistsException::new);
    }

    public void deleteByDni(Integer dni)
            throws BackofficeNotExistsException {
        Backoffice backoffice = this.getByDni(dni);
        backofficeRepository.deleteById(backoffice.getId());
    }

}
