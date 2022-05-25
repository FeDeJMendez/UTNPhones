package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.BackofficeExistsException;
import com.utn.UTNPhones.exceptions.BackofficeNoExistsException;
import com.utn.UTNPhones.exceptions.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BackofficeControllerAdvice {

    @ExceptionHandler(value = {BackofficeExistsException.class})
    protected ResponseEntity<ErrorBody> backofficeExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE BACKOFFICE ALREADY EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {BackofficeNoExistsException.class})
    protected ResponseEntity<ErrorBody> backofficeNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE BACKOFFICE NO EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

}
