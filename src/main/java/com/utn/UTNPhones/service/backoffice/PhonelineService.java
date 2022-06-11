package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.exceptions.PhonelineAssociatedCallsException;
import com.utn.UTNPhones.exceptions.PhonelineExistsException;
import com.utn.UTNPhones.exceptions.PhonelineLengthException;
import com.utn.UTNPhones.exceptions.PhonelineNotExistsException;
import com.utn.UTNPhones.repository.PhonelineRepository;
import org.hibernate.exception.GenericJDBCException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PhonelineService {
    private final PhonelineRepository phonelineRepository;


    @Autowired
    public PhonelineService(PhonelineRepository lineRepository, ModelMapper modelMapper) {
        this.phonelineRepository = lineRepository;
    }


    public Phoneline addPhoneline(Phoneline newPhoneline)
            throws PhonelineExistsException, SQLException, PhonelineLengthException {
        if (newPhoneline.getNumber().length() != 10)
            throw new PhonelineLengthException();
        if (phonelineRepository.existsByNumber(newPhoneline.getNumber()))
            throw new PhonelineExistsException();
        try {
            return phonelineRepository.save(newPhoneline);
        } catch (GenericJDBCException ex) {
            throw new SQLException(ex.getSQLException());
        }
    }

    public Page<Phoneline> getAll(Pageable pageable) {
        return phonelineRepository.findAll(pageable);
    }

    public Phoneline getById (Integer id)
            throws PhonelineNotExistsException {
        return phonelineRepository.findById(id).orElseThrow(PhonelineNotExistsException::new);
    }

    public Phoneline getByNumber (String number)
            throws PhonelineNotExistsException {
        return phonelineRepository.findByNumber(number)
                .orElseThrow(PhonelineNotExistsException::new);
    }

    public void lowPhoneline(String number)
            throws PhonelineNotExistsException {
        Phoneline phoneline = phonelineRepository.findByNumber(number)
                .orElseThrow(PhonelineNotExistsException::new);
        phoneline.setStatus(false);
        phonelineRepository.save(phoneline);
    }

    public void highPhoneline(String number)
            throws PhonelineNotExistsException {
        Phoneline phoneline = phonelineRepository.findByNumber(number)
                .orElseThrow(PhonelineNotExistsException::new);
        phoneline.setStatus(true);
        phonelineRepository.save(phoneline);
    }

    public void deletePhonelineByNumber(String number)
            throws PhonelineNotExistsException, PhonelineAssociatedCallsException {
        Phoneline phoneline = phonelineRepository.findByNumber(number)
                .orElseThrow(PhonelineNotExistsException::new);
        if (phoneline.getOrigins().isEmpty()
                && (phoneline.getDestinations().isEmpty()))
            phonelineRepository.deleteById(phoneline.getId());
        else throw new PhonelineAssociatedCallsException();

    }
}
