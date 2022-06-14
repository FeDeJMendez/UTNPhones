package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Rate;
import com.utn.UTNPhones.dto.RateDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.roles.RateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.utn.UTNPhones.TestUtils.RateTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RateControllerTest {

    private RateService rateService;

    private RateController rateController;

    @Before
    public void setUp() {
        this.rateService = mock(RateService.class);
        this.rateController = new RateController(rateService);
    }

    ///// newRate /////

    @Test
    public void testNewRateOk ()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        //BEHAVIORS//
        when(rateService.addRate(getRateReceived())).thenReturn(getRate());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = rateController.newRate(getRateDtoReceived());
            //ASSERTS//
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = CityIsRequiredException.class)
    public void testNewRateOriginCityIsRequiredException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        RateDto rateDtoWithoutCityOrigin = getRateDtoReceived();
        rateDtoWithoutCityOrigin.setOrigin(null);
        ResponseEntity responseEntity = rateController.newRate(rateDtoWithoutCityOrigin);
    }

    @Test(expected = CityIsRequiredException.class)
    public void testNewRateDestinationCityIsRequiredException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        RateDto rateDtoWithoutCityDestination = getRateDtoReceived();
        rateDtoWithoutCityDestination.setDestination(null);
        ResponseEntity responseEntity = rateController.newRate(rateDtoWithoutCityDestination);
    }

    @Test(expected = RatePriceIsRequiredException.class)
    public void testNewRatePriceIsRequiredException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        RateDto rateDtoWithoutPrince = getRateDtoReceived();
        rateDtoWithoutPrince.setPrice(null);
        ResponseEntity responseEntity = rateController.newRate(rateDtoWithoutPrince);
    }

    @Test(expected = RateTimeRangeIsRequiredException.class)
    public void testNewRateStartTimeRangeIsRequiredException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        RateDto rateDtoWithoutStartTimeRange = getRateDtoReceived();
        rateDtoWithoutStartTimeRange.setStarttime(null);
        ResponseEntity responseEntity = rateController.newRate(rateDtoWithoutStartTimeRange);
    }

    @Test(expected = RateTimeRangeIsRequiredException.class)
    public void testNewRateEndTimeRangeIsRequiredException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException {
        RateDto rateDtoWithoutEndTimeRange = getRateDtoReceived();
        rateDtoWithoutEndTimeRange.setEndtime(null);
        ResponseEntity responseEntity = rateController.newRate(rateDtoWithoutEndTimeRange);
    }

    @Test(expected = RateTimeRangeInUseException.class)
    public void testNewRateTimeRangeInUseException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException  {
        when(rateService.addRate(any())).thenThrow(new RateTimeRangeInUseException());
        ResponseEntity responseEntity = rateController.newRate(getRateDtoReceived());
    }

    @Test(expected = RatePriceNegativeException.class)
    public void testNewRatePriceNegativeException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException  {
        when(rateService.addRate(any())).thenThrow(new RatePriceNegativeException());
        ResponseEntity responseEntity = rateController.newRate(getRateDtoReceived());
    }

    @Test(expected = CityNotExistsException.class)
    public void testNewRateCityNotExistsException()
            throws CityIsRequiredException, RatePriceIsRequiredException, RateTimeRangeIsRequiredException,
            RateTimeRangeInUseException, RatePriceNegativeException, CityNotExistsException  {
        when(rateService.addRate(any())).thenThrow(new CityNotExistsException());
        ResponseEntity responseEntity = rateController.newRate(getRateDtoReceived());
    }

    ///// getAllRates /////

    @Test
    public void testGetAllRatesOk() {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Rate> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getRateList());
        when(rateService.getAll(pageable)).thenReturn(mockedPage);
        try {
            ///EXECUTION///
            ResponseEntity<List<RateDto>> responseEntity = rateController.getAllRates(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getRateList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// deleteRateById /////
    @Test
    public void testDeleteRateByIdOk()
            throws RateNotExistsException {
        doNothing().when(rateService).deleteById(1);
        try {
            ResponseEntity responseEntity = rateController.deleteRateById(1);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(rateService, times(1)).deleteById(any());
        } catch (RateNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = RateNotExistsException.class)
    public void testDeleteRateByIdNotExistsException()
            throws RateNotExistsException {
        doThrow(new RateNotExistsException()).when(rateService).deleteById(anyInt());
        ResponseEntity responseEntity = rateController.deleteRateById(1);
    }

}
