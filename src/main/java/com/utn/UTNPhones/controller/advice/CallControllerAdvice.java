package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.CallDurationIsRequiredException;
import com.utn.UTNPhones.exceptions.CallStarttimeIsRequiredException;
import com.utn.UTNPhones.exceptions.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CallControllerAdvice {

    @ExceptionHandler(value = {CallStarttimeIsRequiredException.class})
    protected ResponseEntity<ErrorBody> callStarttimeIsRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE START TIME IS REQUIRED")
                .build()
                , HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {CallDurationIsRequiredException.class})
    protected ResponseEntity<ErrorBody> callDurationIsRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CALL DURATION IS REQUIRED")
                .build()
                ,HttpStatus.NOT_FOUND
        );
    }

}
