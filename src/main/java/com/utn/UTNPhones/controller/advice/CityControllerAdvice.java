package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CityControllerAdvice {

    @ExceptionHandler(value = {CityExistsException.class})
    protected ResponseEntity<ErrorBody> cityExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CITY ALREADY EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {CityNotExistsException.class})
    protected ResponseEntity<ErrorBody> cityNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CITY DOES NOT EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {CityIsRequiredException.class})
    protected ResponseEntity<ErrorBody> cityIsRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CITY IS REQUIRED")
                .build()
                ,HttpStatus.NOT_FOUND
        );
    }
}
