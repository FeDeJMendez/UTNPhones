package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.dto.PhonelineDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.roles.PhonelineService;
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

import java.sql.SQLException;
import java.util.List;

import static com.utn.UTNPhones.TestUtils.PhonelineTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhonelineControllerTest {

    private PhonelineService phonelineService;

    private PhonelineController phonelineController;

    @Before
    public void setUp() {
        this.phonelineService = mock(PhonelineService.class);
        this.phonelineController = new PhonelineController(phonelineService);
    }

    ///// addPhoneline /////

    @Test
    public void testAddPhonelineOk ()
            throws PhonelineExistsException, SQLException, PhonelineLengthException {
        //BEHAVIORS//
        when(phonelineService.addPhoneline(getPhonelineReceived())).thenReturn(getPhoneline());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = phonelineController.addPhoneline(getPhonelineDtoReceived());
            //ASSERTS//
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = PhonelineExistsException.class)
    public void testAddPhonelineExistsException()
            throws PhonelineExistsException, SQLException, PhonelineLengthException  {
        when(phonelineService.addPhoneline(any())).thenThrow(new PhonelineExistsException());
        ResponseEntity responseEntity = phonelineController.addPhoneline(getPhonelineDtoReceived());
    }

    @Test(expected = SQLException.class)
    public void testAddPhonelineSQLException()
            throws PhonelineExistsException, SQLException, PhonelineLengthException  {
        when(phonelineService.addPhoneline(any())).thenThrow(new SQLException());
        ResponseEntity responseEntity = phonelineController.addPhoneline(getPhonelineDtoReceived());
    }

    @Test(expected = PhonelineLengthException.class)
    public void testAddPhonelineLengthException()
            throws PhonelineExistsException, SQLException, PhonelineLengthException  {
        when(phonelineService.addPhoneline(any())).thenThrow(new PhonelineLengthException());
        ResponseEntity responseEntity = phonelineController.addPhoneline(getPhonelineDtoReceived());
    }

    ///// allPhonelines /////

    @Test
    public void testGetAllPhonelinesOk() {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Phoneline> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getPhonelineList());
        when(phonelineService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<PhonelineDto>> responseEntity = phonelineController.getAllPhonelines(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getPhonelineList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// deletePhonelineBynumber /////

    @Test
    public void testDeletePhonelineByNumberOk()
            throws PhonelineNotExistsException, PhonelineAssociatedCallsException {
        doNothing().when(phonelineService).deletePhonelineByNumber("2235429130");
        try {
            ResponseEntity responseEntity = phonelineController.deletePhonelineByNumber("2235429130");
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(phonelineService, times(1)).deletePhonelineByNumber(any());
        } catch (PhonelineNotExistsException | PhonelineAssociatedCallsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testDeletePhonelineByNumberNotExistsException()
            throws PhonelineNotExistsException, PhonelineAssociatedCallsException {
        doThrow(new PhonelineNotExistsException()).when(phonelineService).deletePhonelineByNumber(anyString());
        ResponseEntity responseEntity = phonelineController.deletePhonelineByNumber("2235123456");
    }

    @Test(expected = PhonelineAssociatedCallsException.class)
    public void testDeletePhonelineByNumberAssociatedCallsException()
            throws PhonelineNotExistsException, PhonelineAssociatedCallsException {
        doThrow(new PhonelineAssociatedCallsException()).when(phonelineService).deletePhonelineByNumber(anyString());
        ResponseEntity responseEntity = phonelineController.deletePhonelineByNumber("2235429130");
    }

    ///// lowPhoneline /////

    @Test
    public void testLowPhonelineOk ()
            throws PhonelineNotExistsException {
        doNothing().when(phonelineService).lowPhoneline("2235429130");
        try {
            ResponseEntity responseEntity = phonelineController.lowPhoneline("2235429130");
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(phonelineService, times(1)).lowPhoneline(any());
        } catch (PhonelineNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testLowPhonelineNotExistsException()
            throws PhonelineNotExistsException {
        doThrow(new PhonelineNotExistsException()).when(phonelineService).lowPhoneline(anyString());
        ResponseEntity responseEntity = phonelineController.lowPhoneline("2235123456");
    }

    ///// highPhoneline /////

    @Test
    public void testHighPhonelineOk ()
            throws PhonelineNotExistsException {
        doNothing().when(phonelineService).highPhoneline("2235429130");
        try {
            ResponseEntity responseEntity = phonelineController.highPhoneline("2235429130");
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(phonelineService, times(1)).highPhoneline(any());
        } catch (PhonelineNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testHighPhonelineNotExistsException()
            throws PhonelineNotExistsException {
        doThrow(new PhonelineNotExistsException()).when(phonelineService).highPhoneline(anyString());
        ResponseEntity responseEntity = phonelineController.highPhoneline("2235123456");
    }

}
