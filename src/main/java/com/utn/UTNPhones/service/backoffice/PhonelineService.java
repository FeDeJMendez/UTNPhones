package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.exceptions.PhonelineExistsException;
import com.utn.UTNPhones.exceptions.PhonelineNoExistsException;
import com.utn.UTNPhones.repository.PhonelineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PhonelineService {
    private final PhonelineRepository phonelineRepository;


    @Autowired
    public PhonelineService(PhonelineRepository lineRepository, ModelMapper modelMapper) {
        this.phonelineRepository = lineRepository;
    }


    public Phoneline addLine(Phoneline newPhoneline)
            throws PhonelineExistsException {
        if (phonelineRepository.existsByNumber(newPhoneline.getNumber()))
            throw new PhonelineExistsException();
        newPhoneline.setStatus(true);
        return phonelineRepository.save(newPhoneline);

    }

    public Page<Phoneline> getAll(Pageable pageable) {
        return phonelineRepository.findAll(pageable);
    }

    public Phoneline getById (Integer id)
            throws PhonelineNoExistsException {
        return phonelineRepository.findById(id).orElseThrow(PhonelineNoExistsException::new);
    }

    public Phoneline getByNumber (String number)
            throws PhonelineNoExistsException {
        return phonelineRepository.findByNumber(number)
                .orElseThrow(PhonelineNoExistsException::new);
    }
}
