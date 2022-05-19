package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.service.backoffice.CityService;
import com.utn.UTNPhones.utils.URIInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice/cities")
public class CityController {
    private final CityService cityService;
    private final ModelMapper modelMapper;

    @Autowired
    public CityController(CityService cityService, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    ///// Save new city /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addCity (@RequestBody @Validated final CityDto cityDto)
            throws CityExistsException {
        City newCity = cityService.addCity(modelMapper.map(cityDto, City.class));
        return ResponseEntity.created(Conf.getLocation(newCity)).build();
    }

    ///// Get All Cities /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<CityDto>> allCities (Pageable pageable) {
        Page page = cityService.getAll(pageable);
        return Conf.Response(page);
    }
}

