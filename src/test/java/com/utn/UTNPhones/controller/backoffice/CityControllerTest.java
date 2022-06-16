package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.City;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.exceptions.ProvinceIsRequiredException;
import com.utn.UTNPhones.exceptions.ProvinceNotExistsException;
import com.utn.UTNPhones.service.roles.CityService;
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

import static com.utn.UTNPhones.TestUtils.CityTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityControllerTest {

    private CityService cityService;

   private CityController cityController;

    @Before
    public void setUp() {
        this.cityService = mock(CityService.class);
        this.cityController = new CityController(cityService);
    }


    ///// addCity /////

    @Test
    public void testAddCityOk()
            throws CityExistsException, ProvinceNotExistsException, ProvinceIsRequiredException {
        //BEHAVIORS//
        when(cityService.addCity(getCityReceived())).thenReturn(getCity());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = cityController.addCity(getCityDtoReceived());
            //ASSERTS//
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = CityExistsException.class)
    public void testAddCityExistsException()
            throws CityExistsException, ProvinceNotExistsException, ProvinceIsRequiredException  {
        when(cityService.addCity(any())).thenThrow(new CityExistsException());
        ResponseEntity responseEntity = cityController.addCity(getCityDtoReceived());
    }

    @Test(expected = ProvinceNotExistsException.class)
    public void testAddCityProvinceNotExistsException()
            throws CityExistsException, ProvinceNotExistsException, ProvinceIsRequiredException  {
        when(cityService.addCity(any())).thenThrow(new ProvinceNotExistsException());
        ResponseEntity responseEntity = cityController.addCity(getCityDtoReceived());
    }

    @Test(expected = ProvinceIsRequiredException.class)
    public void testAddCityProvinceIsRequiredException()
            throws CityExistsException, ProvinceNotExistsException, ProvinceIsRequiredException  {
        CityDto cityDtoWithoutProvince = getCityDtoReceived();
        cityDtoWithoutProvince.setProvince(null);
        ResponseEntity responseEntity = cityController.addCity(cityDtoWithoutProvince);
    }

    ///// getAllCities /////

    @Test
    public void testGetAllCitiesOk () {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<City> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getCityList());
        when(cityService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<CityDto>> responseEntity = cityController.getAllCities(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getCityList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

}
