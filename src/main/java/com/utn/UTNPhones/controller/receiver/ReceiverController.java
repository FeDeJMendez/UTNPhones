package com.utn.UTNPhones.controller.receiver;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.backoffice.CallService;
import com.utn.UTNPhones.service.backoffice.PhonelineService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/calls")
public class ReceiverController {
    private final CallService callService;
    private final PhonelineService phonelineService;

    public ReceiverController(CallService callService, PhonelineService phonelineService) {
        this.callService = callService;
        this.phonelineService = phonelineService;
    }

    @Data
    private static class CallReceiverDto{
        String origin;
        String destination;
        String datetime;
        Integer duration;
    }


    ///// Receiver New Calls /////
    @PostMapping(consumes = "application/json")
    public ResponseEntity receiveCall (@RequestBody @Validated final CallReceiverDto callReceiverDto)
            throws PhonelineNotExistsException, PhonelineEqualException, PhonelineRequiredException, CallStarttimeIsRequiredException,
            CallDurationIsRequiredException, PhonelineOriginLowException, PhonelineDestinationLowException, SQLException {
        Phoneline origin = phonelineService.getByNumber(callReceiverDto.getOrigin());
        Phoneline destination = phonelineService.getByNumber(callReceiverDto.getDestination());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime starttime = LocalDateTime.parse(callReceiverDto.getDatetime(), formatter);
        Call newCall = callService.addCall(Call.builder()
                .starttime(starttime)
                .duration(callReceiverDto.getDuration())
                .origin(origin)
                .destination(destination)
                .build());
        return ResponseEntity.created(Conf.getLocation(newCall)).build();
    }
}
