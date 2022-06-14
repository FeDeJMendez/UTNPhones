package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.service.roles.BillService;
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

import static com.utn.UTNPhones.TestUtils.BillTestUtils.getBillDtoList;
import static com.utn.UTNPhones.TestUtils.BillTestUtils.getBillList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//@RunWith(PowerMockRunner.class)
//@PrepareForTest(Conf.class)
public class BillControllerTest {

    private BillService billService;

    private BillController billController;

    @Before
    public void setUp() {
        this.billService = mock(BillService.class);
        this.billController = new BillController(billService);
    }

    ///// getAllBills /////

    @Test
    public void testGetAllBillsOk() {
        ///BEHAVIORS///
        Pageable pageable = PageRequest.of(1, 10);
        Page<Bill> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(getBillList());
        when(billService.getAll(pageable)).thenReturn(mockedPage);
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getAllBills(pageable);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(100L, Long.parseLong(responseEntity.getHeaders().get("X-Total-Count").get(0)));
            assertEquals(10, Integer.parseInt(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
            assertEquals(getBillList(), responseEntity.getBody());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    ///// getByClient /////

    @Test
    public void testGetByClientOk()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        when(billService.getByClient(1)).thenReturn(getBillList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getByClient(1);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(getBillDtoList(), responseEntity.getBody());
            assertEquals(2, responseEntity.getBody().size());
            assertEquals((Double) 121.00, responseEntity.getBody().get(0).getTotalprice());
            assertEquals((Double) 242.00, responseEntity.getBody().get(1).getTotalprice());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetByClientNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        when(billService.getByClient(2)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getByClient(2);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetByClientNotExistsException()
            throws ClientNotExistsException{
        when(billService.getByClient(10)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = billController.getByClient(10);
    }

    ///// getBillsByClientBetweenDates /////

    @Test
    public void testGetBillsByUserBetweenDatesOk()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(getBillList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getBillsByClientBetweenDates(1, startDate, endDate);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(getBillDtoList(), responseEntity.getBody());
            assertEquals(2, responseEntity.getBody().size());
            assertEquals((Double) 121.00, responseEntity.getBody().get(0).getTotalprice());
            assertEquals((Double) 242.00, responseEntity.getBody().get(1).getTotalprice());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetBillsByUserBetweenDatesNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getBillsByClientBetweenDates(1, startDate, endDate);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetBillsByUserBetweenDatesNotExistsException()
            throws ClientNotExistsException{
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        when(billService.getByClientBetweenDates(1, startDate, endDate)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = billController.getBillsByClientBetweenDates(1, startDate, endDate);
    }

    ///// getUnpaidBillsByClient /////

    @Test
    public void testGetUnpaidBillsByClientOk()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        when(billService.getUnpaidByClient(1)).thenReturn(getBillList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getUnpaidBillsByClient(1);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(getBillDtoList(), responseEntity.getBody());
            assertEquals(2, responseEntity.getBody().size());
            assertEquals((Double) 121.00, responseEntity.getBody().get(0).getTotalprice());
            assertEquals((Double) 242.00, responseEntity.getBody().get(1).getTotalprice());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void testGetUnpaidBillsByClientNoContent()
            throws ClientNotExistsException {
        ///BEHAVIORS///
        when(billService.getUnpaidByClient(1)).thenReturn(emptyList());
        try{
            ///EXECUTION///
            ResponseEntity<List<BillDto>> responseEntity = billController.getUnpaidBillsByClient(1);
            ///ASSERTS///
            Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            assertEquals(emptyList(), responseEntity.getBody());
            assertEquals(0, responseEntity.getBody().size());
        } catch (Exception ex) {
            fail("Unexpected Exception!");
        }
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetUnpaidBillsByClientNotExistsException()
            throws ClientNotExistsException{
        when(billService.getUnpaidByClient(1)).thenThrow(new ClientNotExistsException());
        ResponseEntity responseEntity = billController.getUnpaidBillsByClient(1);
    }


}
