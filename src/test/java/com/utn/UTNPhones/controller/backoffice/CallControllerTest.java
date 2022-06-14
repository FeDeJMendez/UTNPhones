package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.exceptions.PhonelineNotExistsException;
import com.utn.UTNPhones.service.roles.CallService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static com.utn.UTNPhones.TestUtils.CallTestUtils.getCallDtoList;
import static com.utn.UTNPhones.TestUtils.CallTestUtils.getCallList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallControllerTest {

    private  CallService callService;

    private CallController callController;

    @Before
    public void setUp() {
        this.callService = mock(CallService.class);
        this.callController = new CallController(callService);
    }

    ///// getAllCalls /////

    @Test
    public void testGetAllCallsOk () {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Call> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getCallList());
        when(callService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = callController.getAllCalls(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getCallList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// getCallsByUserBetweenDates /////

    @Test
    public void testGetCallsByUserBetweenDatesOk ()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(callService.getByClientBetweenDates(1,startDate, endDate)).thenReturn(getCallList());
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = callController.getCallsByUserBetweenDates(1, startDate, endDate);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(getCallDtoList(), responseEntity.getBody());
            assertEquals(2, responseEntity.getBody().size());
            assertEquals((Double) 35.00, responseEntity.getBody().get(0).getTotal());
            assertEquals((Double) 42.00, responseEntity.getBody().get(1).getTotal());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetCallsByUserBetweenDatesNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(callService.getByClientBetweenDates(1,startDate, endDate)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = callController.getCallsByUserBetweenDates(1, startDate, endDate);
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
        when(callService.getByClientBetweenDates(10,startDate, endDate)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = callController.getCallsByUserBetweenDates(10,startDate, endDate);
    }

    ///// getCallsUnbilledByPhonelineNumber /////

    @Test
    public void testGetCallsUnbilledByPhonelineNumberOk ()
            throws PhonelineNotExistsException {
        ///BEHAVIORS///
        when(callService.getUnbilledByPhonelineNumber("2235429130")).thenReturn(getCallList());
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = callController.getCallsUnbilledByPhonelineNumber("2235429130");
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(getCallDtoList(), responseEntity.getBody());
            assertEquals(2, responseEntity.getBody().size());
            assertEquals((Double) 35.00, responseEntity.getBody().get(0).getTotal());
            assertEquals((Double) 42.00, responseEntity.getBody().get(1).getTotal());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetCallsUnbilledByPhonelineNumberNoContent()
            throws PhonelineNotExistsException {
        ///BEHAVIORS///
        when(callService.getUnbilledByPhonelineNumber("2235429130")).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<CallDto>> responseEntity = callController.getCallsUnbilledByPhonelineNumber("2235429130");
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testGetCallsUnbilledByPhonelineNumberClientNotExistsException()
            throws PhonelineNotExistsException{
        when(callService.getUnbilledByPhonelineNumber("2235429130")).thenThrow(new PhonelineNotExistsException());
        ResponseEntity<List<CallDto>> responseEntity = callController.getCallsUnbilledByPhonelineNumber("2235429130");
    }

}
