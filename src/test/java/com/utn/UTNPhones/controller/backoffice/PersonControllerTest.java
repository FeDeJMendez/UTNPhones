package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.dto.BackofficeDto;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.UserService;
import com.utn.UTNPhones.service.roles.BackofficeService;
import com.utn.UTNPhones.service.roles.ClientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.utn.UTNPhones.TestUtils.PersonTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    private ClientService clientService;
    private BackofficeService backofficeService;
    private UserService userService;
    private ModelMapper modelMapper;

    private PersonController personController;

    @Before
    public void setUp() {
        this.clientService = mock(ClientService.class);
        this.backofficeService = mock(BackofficeService.class);
        this.userService = mock(UserService.class);
        this.modelMapper = mock(ModelMapper.class);
        this.personController = new PersonController(clientService, backofficeService, userService, modelMapper);
    }

    ///// addClient /////

    @Test
    public void testAddClientOk ()
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException {
        //BEHAVIORS//
        when(clientService.addClient(getClientReceived())).thenReturn(getClient());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = personController.addClient(getClientDtoReceived());
            //ASSERTS//
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientExistsException.class)
    public void testAddClientExistsException()
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException  {
        when(clientService.addClient(any())).thenThrow(new ClientExistsException());
        ResponseEntity responseEntity = personController.addClient(getClientDtoReceived());
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testAddClientPhonelineNotExistsException()
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException  {
        when(clientService.addClient(any())).thenThrow(new PhonelineNotExistsException());
        ResponseEntity responseEntity = personController.addClient(getClientDtoReceived());
    }

    @Test(expected = PhonelineBadDataException.class)
    public void testAddClientPhonelineBadDataException()
            throws ClientExistsException, PhonelineNotExistsException, PhonelineBadDataException  {
        when(clientService.addClient(any())).thenThrow(new PhonelineBadDataException());
        ResponseEntity responseEntity = personController.addClient(getClientDtoReceived());
    }

    ///// getAllClients /////

    @Test
    public void testGetAllClientsOk() {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Client> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getClientList());
        when(clientService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<ClientDto>> responseEntity = personController.getAllClients(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getClientList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// deleteClientByDni /////

    @Test
    public void testDeleteClientByDniOk()
            throws ClientNotExistsException{
        doNothing().when(clientService).deleteByDni(1);
        try {
            ResponseEntity responseEntity = personController.deleteClientByDni(1);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(clientService, times(1)).deleteByDni(any());
        } catch (ClientNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testDeleteClientNotExistsException()
            throws ClientNotExistsException{
        doThrow(new ClientNotExistsException()).when(clientService).deleteByDni(anyInt());
        ResponseEntity responseEntity = personController.deleteClientByDni(0);
    }

    ///// editClient /////

    @Test
    public void testEditClientOk ()
            throws ClientNotExistsException {
        //BEHAVIORS//
        when(clientService.editClient(getClientReceived(),34058251)).thenReturn(getClient());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = personController.editClient(getClientDtoReceived(), 34058251);
            //ASSERTS//
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testEditClientNotExistsException()
            throws ClientNotExistsException{
        doThrow(new ClientNotExistsException()).when(clientService).editClient(any(), anyInt());
        ResponseEntity responseEntity = personController.editClient(getClientDtoReceived(), 12345678);
    }

    ///// assignLineToClient /////

    @Test
    public void testAssignLineToClientOk ()
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        //BEHAVIORS//
        doNothing().when(clientService).assignLineToClient(1, "2235429130");
        try {
            //EXECUTION//
            ResponseEntity responseEntity = personController.assignLineToClient(1, "2235429130");
            //ASSERTS//
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testAssignLineToClientNotExistsException()
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        doThrow(new ClientNotExistsException()).when(clientService).assignLineToClient(anyInt(), anyString());
        ResponseEntity responseEntity = personController.assignLineToClient(10, "2235429130");
    }

    @Test(expected = PhonelineNotExistsException.class)
    public void testAssignLineToClientPhonelineNotExistsException()
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        doThrow(new PhonelineNotExistsException()).when(clientService).assignLineToClient(anyInt(), anyString());
        ResponseEntity responseEntity = personController.assignLineToClient(1, "1234567890");
    }

    @Test(expected = PhonelineAsignedException.class)
    public void testAssignLineToClientPhonelineAsignedException()
            throws ClientNotExistsException, PhonelineNotExistsException, PhonelineAsignedException {
        doThrow(new PhonelineAsignedException()).when(clientService).assignLineToClient(anyInt(), anyString());
        ResponseEntity responseEntity = personController.assignLineToClient(1, "2235429130");
    }

    ///// deleteLineToClient /////

    @Test
    public void testDeleteLineToClientOk ()
            throws ClientNotExistsException {
        doNothing().when(clientService).deleteLineToClient(1);
        try {
            ResponseEntity responseEntity = personController.deleteLineToClient(1);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(clientService, times(1)).deleteLineToClient(any());
        } catch (ClientNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testDeleteLineToClientNotExistsException()
            throws ClientNotExistsException{
        doThrow(new ClientNotExistsException()).when(clientService).deleteLineToClient(anyInt());
        ResponseEntity responseEntity = personController.deleteLineToClient(0);
    }

    ///// addBackoffice /////

    @Test
    public void testAddBackofficeOk ()
            throws BackofficeExistsException {
        //BEHAVIORS//
        when(backofficeService.addBackoffice(getBackofficeReceived())).thenReturn(getBackoffice());
        try (MockedStatic<Conf> confMockedStatic = Mockito.mockStatic(Conf.class)){
            //EXECUTION//
            ResponseEntity responseEntity = personController.addBackoffice(getBackofficeDtoReceived());
            //ASSERTS//
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
        catch (Exception ex){
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = BackofficeExistsException.class)
    public void testAddBackofficeExistsException()
            throws BackofficeExistsException {
        when(backofficeService.addBackoffice(any())).thenThrow(new BackofficeExistsException());
        ResponseEntity responseEntity = personController.addBackoffice(getBackofficeDtoReceived());
    }

    ///// getAllBackoffice /////

    @Test
    public void testGetAllBackofficeOk() {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Backoffice> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getBackofficeList());
        when(backofficeService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<BackofficeDto>> responseEntity = personController.getAllBackoffice(pageable);
            ///ASSERTS//
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getBackofficeList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// deleteBackofficeByDni /////

    @Test
    public void testDeleteBackofficeByDniOk()
            throws BackofficeNotExistsException{
        doNothing().when(backofficeService).deleteByDni(1);
        try {
            ResponseEntity responseEntity = personController.deleteBackofficeByDni(1);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(backofficeService, times(1)).deleteByDni(any());
        } catch (BackofficeNotExistsException e) {
            Assert.fail("Unexpected Exception!");
        }
    }

    @Test(expected = BackofficeNotExistsException.class)
    public void testDeleteBackofficeNotExistsException()
            throws BackofficeNotExistsException{
        doThrow(new BackofficeNotExistsException()).when(backofficeService).deleteByDni(anyInt());
        ResponseEntity responseEntity = personController.deleteBackofficeByDni(0);
    }

}
