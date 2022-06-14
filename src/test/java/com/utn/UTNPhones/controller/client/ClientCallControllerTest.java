package com.utn.UTNPhones.controller.client;

import com.utn.UTNPhones.TestUtils.CallTestUtils;
import com.utn.UTNPhones.domain.enums.Rol;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.dto.UserDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.service.roles.CallService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static com.utn.UTNPhones.TestUtils.CallTestUtils.*;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientCallControllerTest {

    private CallService callService;
    private Authentication auth;

    private ClientCallController clientCallController;

    @Before
    public void setUp() {
        this.callService = mock(CallService.class);
        this.auth = mock(Authentication.class);
        this.clientCallController = new ClientCallController(callService);
    }

    ///// getCallsByUserBetweenDates /////

    @Test
    public void testGetCallsByUserBetweenDatesOk ()
            throws ClientNotExistsException {
        //BEHAVIORS//
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(callService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(getCallList());
        try {
            //EXECUTION//
            ResponseEntity<List<CallDto>> responseEntity = clientCallController.getCallsByUserBetweenDates(startDate, endDate, auth);
            //ASSERTS//
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetCallsByUserBetweenDatesNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(callService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = clientCallController.getCallsByUserBetweenDates(startDate, endDate, auth);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetCallsByUserBetweenDatesClientNotExistsException()
            throws ClientNotExistsException{
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(auth.getPrincipal()).thenReturn(UserDto.builder().id(1).username("Mendez").rol(Rol.ROLE_CLIENT).person_id(1).build());
        when(callService.getByClientBetweenDates(1, startDate, endDate)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = clientCallController.getCallsByUserBetweenDates(startDate, endDate, auth);
    }
}
