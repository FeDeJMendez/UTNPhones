package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RateControllerAdvice {
    @ExceptionHandler(value = {RateTimeRangeInUseException.class})
    protected ResponseEntity<ErrorBody> rateTimeRangeInUse () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("THE TIME RANGE IS IN USE")
                .build()
                , HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {RatePriceNegativeException.class})
    protected ResponseEntity<ErrorBody> ratePriceNegative () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("THE PRICE MUST BE POSITIVE")
                .build()
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {RatePriceIsRequiredException.class})
    protected ResponseEntity<ErrorBody> ratePriceIsRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE PRICE IS REQUIRED")
                .build()
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {RateTimeRangeIsRequiredException.class})
    protected ResponseEntity<ErrorBody> rateTimeRangeIsRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE TIME RANGE IS REQUIRED")
                .build()
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {RateNotExistsException.class})
    protected ResponseEntity<ErrorBody> rateNotExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_EXTENDED.value())
                .message("THE RATE DOES NOT EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
