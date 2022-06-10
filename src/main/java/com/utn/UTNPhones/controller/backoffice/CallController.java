package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.backoffice.CallService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/calls")
public class CallController {
    private final CallService callService;
    private final ModelMapper modelMapper;

    @Autowired
    public CallController(CallService callService, ModelMapper modelMapper) {
        this.callService = callService;
        this.modelMapper = modelMapper;
    }

    ///// Add New Call /////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addCall (@RequestBody @Validated final CallDto callDto)
            throws CallStarttimeIsRequiredException, CallDurationIsRequiredException, PhonelineRequiredException,
            PhonelineEqualException, PhonelineDestinationLowException, PhonelineOriginLowException, PhonelineNoExistsException {
        Call newCall = callService.addCall(modelMapper.map(callDto, Call.class));
        return ResponseEntity.created(Conf.getLocation(newCall)).build();
    }

    ///// Get All Calls /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Call>> allCalls (Pageable pageable) {
        Page page = callService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Get All Calls by Client and Time Range /////
    @GetMapping(path = "/clients/{idClient}/dates/{start}/{end}", produces = "application/json")
    public ResponseEntity<List<CallDto>> getCallsByUserBetweenDates (@PathVariable Integer idClient,
                                                                     @PathVariable(value = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                     @PathVariable(value = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate)
            throws ClientNoExistsException {
        List<Call> filteredCalls = callService.getByClientBetweenDates(idClient, startDate, endDate);
        List<CallDto> filteredCallsDto = Conf.listCallsToDto(filteredCalls);
        return ResponseEntity
                .status(filteredCallsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredCallsDto);
    }
}
