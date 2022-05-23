package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.dto.BillDto;
import com.utn.UTNPhones.service.backoffice.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
