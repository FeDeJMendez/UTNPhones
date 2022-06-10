package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.*;
import org.hibernate.JDBCException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

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
