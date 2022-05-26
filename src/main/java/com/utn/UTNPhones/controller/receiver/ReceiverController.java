package com.utn.UTNPhones.controller.receiver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.Line;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.service.backoffice.CallService;
import com.utn.UTNPhones.service.backoffice.LineService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/calls")
public class ReceiverController {
    private final CallService callService;
    private final LineService lineService;

    public ReceiverController(CallService callService, LineService lineService) {
        this.callService = callService;
        this.lineService = lineService;
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
            throws LineNoExistsException, LineEqualException, LineRequiredException, CallStartIsRequiredException,
            CallDurationIsRequiredException, LineOriginLowException, LineDestinationLowException {
        Line origin = lineService.getByNumber(callReceiverDto.getOrigin());
        Line destination = lineService.getByNumber(callReceiverDto.getDestination());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(callReceiverDto.getDatetime(), formatter);
        Call newCall = callService.addCall(Call.builder()
                .start(start)
                .duration(callReceiverDto.getDuration())
                .origin(origin)
                .destination(destination)
                .build());
        return ResponseEntity.created(Conf.getLocation(newCall)).build();
    }
}

//@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
