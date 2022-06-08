package com.utn.UTNPhones.controller.client;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.dto.UserDto;
import com.utn.UTNPhones.service.backoffice.CallService;
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
@RequestMapping(value = "/api/client/calls")
public class ClientCallController {
    private final CallService callService;

    @Autowired
    public ClientCallController(CallService callService) {
        this.callService = callService;
    }

    ///// Get All Calls Between Dates /////
    @GetMapping(path = "/dates/{start}/{end}", produces = "application/json")
    public ResponseEntity<List<CallDto>> getCallsByUserBetweenDates (@PathVariable(value = "start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                     @PathVariable(value = "end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        UserDto userDto = new UserDto(); userDto.setPerson_id(1); /*(UserDto) auth.getPrincipal();*/
        //// Change this when apply login !!!!!
        List<Call> filteredCalls = callService.getByClientBetweenDates(userDto.getPerson_id(),startDate, endDate);
        List<CallDto> filteredCallsDto = Conf.listCallsToDto(filteredCalls);
        return ResponseEntity
                .status(filteredCallsDto.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT)
                .body(filteredCallsDto);
    }

}
