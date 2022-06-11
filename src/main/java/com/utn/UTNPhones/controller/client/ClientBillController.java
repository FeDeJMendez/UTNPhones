package com.utn.UTNPhones.controller.client;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.dto.UserDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.service.backoffice.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/client/bills")
public class ClientBillController {

    private final BillService billService;

    @Autowired
    public ClientBillController(BillService billService) {
        this.billService = billService;
    }

    ///// Get All Bills Between Dates /////
    @GetMapping(path = "/dates/{start}/{end}", produces = "application/json")
    public ResponseEntity<List<BillDto>> getBillsByUserBetweenDates (@PathVariable(value = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                     @PathVariable(value = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate)
            throws ClientNotExistsException {
        UserDto userDto = new UserDto(); userDto.setPerson_id(1); /*(UserDto) auth.getPrincipal();*/
        //// Change this when apply login !!!!!
        List<Bill> filteredBills = billService.getByClientBetweenDates(userDto.getPerson_id(),startDate, endDate);
        List<BillDto> filteredBillsDto = Conf.listBillsToDto(filteredBills);
        return ResponseEntity
                .status(filteredBillsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredBillsDto);
    }

}
