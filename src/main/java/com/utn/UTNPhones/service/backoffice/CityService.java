package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.exceptions.CityNoExistsException;
import com.utn.UTNPhones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    public City addCity(City newCity) throws CityExistsException {
        if(cityRepository.existsByPrefix(newCity.getPrefix()))
            throw new CityExistsException();
        return cityRepository.save(newCity);
    }

    public Page<City> getAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public City getById(Integer id)
            throws CityNoExistsException {
        return cityRepository.findById(id)
                .orElseThrow(CityNoExistsException::new);
    }

    public Boolean existsById (Integer id) {
        return cityRepository.existsById(id);
    }
}
