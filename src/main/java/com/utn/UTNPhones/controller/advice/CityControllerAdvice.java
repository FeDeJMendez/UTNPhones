package com.utn.UTNPhones.controller.advice;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.utn.UTNPhones.exceptions.CityExistsException;
import com.utn.UTNPhones.exceptions.CityIsRequiredException;
import com.utn.UTNPhones.exceptions.CityNoExistsException;
import com.utn.UTNPhones.exceptions.ErrorBody;
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

    @ExceptionHandler(value = {CityNoExistsException.class})
    protected ResponseEntity<ErrorBody> cityNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CITY NO EXISTS")
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
