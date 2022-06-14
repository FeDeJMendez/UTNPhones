package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Province;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.exceptions.ProvinceIsRequiredException;
import com.utn.UTNPhones.exceptions.ProvinceNotExistsException;
import com.utn.UTNPhones.service.roles.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    ///// Save new city /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addCity (@RequestBody @Validated final CityDto cityDto)
            throws CityExistsException, ProvinceIsRequiredException, ProvinceNotExistsException {
        if (cityDto.getProvince() == null)
            throw new ProvinceIsRequiredException();
//        City newCity = cityService.addCity(modelMapper.map(cityDto, City.class));
        City newCity = cityService.addCity(City.from(cityDto));
        return ResponseEntity.created(Conf.getLocation(newCity)).build();
    }

    ///// Get All Cities /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<CityDto>> getAllCities (Pageable pageable) {
        Page page = cityService.getAll(pageable);
        return Conf.response(page);
    }
}

