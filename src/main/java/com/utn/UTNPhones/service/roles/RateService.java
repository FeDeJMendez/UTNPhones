package com.utn.UTNPhones.service.roles;

import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.domain.Rate;
import com.utn.UTNPhones.exceptions.CityNotExistsException;
import com.utn.UTNPhones.exceptions.RateNotExistsException;
import com.utn.UTNPhones.exceptions.RatePriceNegativeException;
import com.utn.UTNPhones.exceptions.RateTimeRangeInUseException;
import com.utn.UTNPhones.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class RateService {
    private final RateRepository rateRepository;
    private final CityService cityService;

    @Autowired
    public RateService(RateRepository rateRepository, CityService cityService) {
        this.rateRepository = rateRepository;
        this.cityService = cityService;
    }


    public Rate addRate(Rate newRate)
            throws RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        if (newRate.getPrice() < 0)
            throw new RatePriceNegativeException();

        List<Rate> rates = rateRepository.findByOriginAndDestination(newRate.getOrigin().getId(), newRate.getDestination().getId());
        LocalTime newStarttime = newRate.getStarttime();
        LocalTime newEndtime = newRate.getEndtime();
        for (Rate rate : rates) {
            LocalTime starttime = rate.getStarttime();
            LocalTime endtime = rate.getEndtime();
            if ((newStarttime.compareTo(starttime) < 0 && newEndtime.compareTo(starttime) > 0) ||
                    (newStarttime.compareTo(endtime) < 0  && newEndtime.compareTo(endtime) > 0)) {
                throw new RateTimeRangeInUseException();
            }
        }

        /*if ((!cityService.existsById(newRate.getOrigin().getId())) ||
                (!cityService.existsById(newRate.getDestination().getId()))) {
            throw new CityNoExistsException();
        }*/
        City origin = cityService.getById(newRate.getOrigin().getId());
        City destination = cityService.getById(newRate.getDestination().getId());
        newRate.setOrigin(origin);
        newRate.setDestination(destination);
        return rateRepository.save(newRate);
    }

    public Page<Rate> getAll(Pageable pageable) {
        return rateRepository.findAll(pageable);
    }

    public void deleteById(Integer id)
            throws RateNotExistsException {
        if (!rateRepository.existsById(id))
            throw new RateNotExistsException();
        rateRepository.deleteById(id);
    }
}