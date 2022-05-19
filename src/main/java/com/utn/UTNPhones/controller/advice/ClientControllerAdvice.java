package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.ClientExistsException;
import com.utn.UTNPhones.exceptions.ClientNoExistsException;
import com.utn.UTNPhones.exceptions.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientControllerAdvice {

    @ExceptionHandler(value = {ClientExistsException.class})
    protected ResponseEntity<ErrorBody> clientExists () {
        return new ResponseEntity(ErrorBody.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("THE CLIENT ALREADY EXISTS")
                    .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {ClientNoExistsException.class})
    protected ResponseEntity<ErrorBody> clientNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE CLIENT NO EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
