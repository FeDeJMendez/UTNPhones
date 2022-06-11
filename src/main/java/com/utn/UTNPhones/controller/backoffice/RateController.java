package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Rate;
import com.utn.UTNPhones.dto.RateDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.backoffice.CityService;
import com.utn.UTNPhones.service.backoffice.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/rates")
public class RateController {
    private final RateService rateService;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    @Autowired
    public RateController(RateService rateService, CityService cityService, ModelMapper modelMapper) {
        this.rateService = rateService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }


    ///// Add new Rate /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity newRate (@RequestBody @Validated final RateDto rateDto)
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        if ((rateDto.getOrigin() == null) || (rateDto.getDestination() == null))
            throw new CityIsRequiredException();
        if (rateDto.getPrice() == null)
            throw new RatePriceIsRequiredException();
        if ((rateDto.getStarttime() == null) || (rateDto.getEndtime() == null))
            throw new RateTimeRangeIsRequiredException();
        Rate newRate = rateService.addRate(modelMapper.map(rateDto, Rate.class)); // DOES NOT MAP CITIES WELL
        /*Rate newRate = rateService.addRate(Rate.builder()
                .price(rateDto.getPrice())
                .start(rateDto.getStart())
                .end(rateDto.getEnd())
                .origin(cityService.getById(rateDto.getOrigin().getId()))
                .destination(cityService.getById(rateDto.getDestination().getId()))
                .build());*/
        return ResponseEntity.created(Conf.getLocation(newRate)).build();
    }

    ///// Get All Rates /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Rate>> allRates (Pageable pageable) {
        Page<Rate> page = rateService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Delete Rate /////
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity deleteRateById(@PathVariable(value = "id") Integer id)
            throws RateNotExistsException {
        rateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
