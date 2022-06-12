package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.service.roles.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/bills")
public class BillController {
    private final BillService billService;
    private final ModelMapper modelMapper;

    @Autowired
    public BillController(BillService billService, ModelMapper modelMapper) {
        this.billService = billService;
        this.modelMapper = modelMapper;
    }

    ///// Add New  Bill /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addBill (@RequestBody @Validated final BillDto billDto) {
        Bill newBill = billService.addBill(modelMapper.map(billDto, Bill.class));
        return ResponseEntity.created(Conf.getLocation(newBill)).build();
    }

    ///// Get All Bills /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Bill>> allBills (Pageable pageable) {
        Page page = billService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Get Bills by Client /////
    @GetMapping(path = "/clients/{idClient}", produces = "application/json")
    public ResponseEntity<List<BillDto>> byClient (@PathVariable Integer idClient)
            throws ClientNotExistsException {
        List<Bill> filteredBills = billService.getByClient(idClient);
        List<BillDto> filteredCallsDto = Conf.listBillsToDto(filteredBills);
        return ResponseEntity
                .status(filteredCallsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredCallsDto);
    }

    ///// Get Bills by Client and Time Range (Between Dates) /////
    @GetMapping(path = "/clients/{idClient}/dates/{start}/{end}", produces = "application/json")
    public ResponseEntity<List<BillDto>> getBillsByUserBetweenDates (@PathVariable Integer idClient,
                                                                     @PathVariable(value = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                     @PathVariable(value = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate)
            throws ClientNotExistsException {
        List<Bill> filteredBills = billService.getByClientBetweenDates(idClient, startDate, endDate);
        List<BillDto> filteredCallsDto = Conf.listBillsToDto(filteredBills);
        return ResponseEntity
                .status(filteredCallsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredCallsDto);
    }

    ///// Get Unpaid Bills by Client /////
    @GetMapping(path = "/clients/{idClient}/unpaid", produces = "application/json")
    public ResponseEntity<List<BillDto>> getUnpaidBillsByClient(@PathVariable Integer idClient)
            throws ClientNotExistsException {
        List<Bill> filteredBills = billService.getUnpaidByClient(idClient);
        List<BillDto> filteredCallsDto = Conf.listBillsToDto(filteredBills);
        return ResponseEntity
                .status(filteredCallsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredCallsDto);
    }

}
