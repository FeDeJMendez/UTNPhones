package com.utn.UTNPhones.controller.client;

import com.utn.UTNPhones.TestUtils.BillTestUtils;
import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.controller.backoffice.BillController;
import com.utn.UTNPhones.controller.backoffice.RateController;
import com.utn.UTNPhones.domain.enums.Rol;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.dto.UserDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.service.roles.BillService;
import com.utn.UTNPhones.service.roles.RateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static com.utn.UTNPhones.TestUtils.BillTestUtils.*;
import static com.utn.UTNPhones.TestUtils.RateTestUtils.*;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientBillControllerTest {

    private BillService billService;
    private Authentication auth;

    private ClientBillController clientBillController;

    @Before
    public void setUp() {
        this.billService = mock(BillService.class);
        this.auth = mock(Authentication.class);
        this.clientBillController = new ClientBillController(billService);
    }

    ///// getBillsByUserBetweenDates /////

    @Test
    public void testGetBillsByUserBetweenDatesOk ()
            throws ClientNotExistsException {
        //BEHAVIORS//
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(getBillList());
        try {
            //EXECUTION//
            ResponseEntity<List<BillDto>> responseEntity = clientBillController.getBillsByUserBetweenDates(startDate, endDate, auth);
            //ASSERTS//
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetBillsByUserBetweenDatesNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = clientBillController.getBillsByUserBetweenDates(startDate, endDate, auth);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetBillsByUserBetweenDatesClientNotExistsException()
            throws ClientNotExistsException{
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = clientBillController.getBillsByUserBetweenDates(startDate, endDate, auth);
    }
}
