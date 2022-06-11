package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Province;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.exceptions.CityNotExistsException;
import com.utn.UTNPhones.exceptions.ProvinceNotExistsException;
import com.utn.UTNPhones.repository.CityRepository;
import com.utn.UTNPhones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public CityService(CityRepository cityRepository, ProvinceRepository provinceRepository) {
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
    }


    public City addCity(City newCity)
            throws CityExistsException, ProvinceNotExistsException {
        Province province = provinceRepository.findById(newCity.getProvince().getId())
                .orElseThrow(ProvinceNotExistsException::new);
        if(cityRepository.existsByPrefix(newCity.getPrefix()))
            throw new CityExistsException();
        return cityRepository.save(newCity);
    }

    public Page<City> getAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public City getById(Integer id)
            throws CityNotExistsException {
        return cityRepository.findById(id)
                .orElseThrow(CityNotExistsException::new);
    }

    public Boolean existsById (Integer id) {
        return cityRepository.existsById(id);
    }
}
