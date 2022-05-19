package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LineControllerAdvice {

    @ExceptionHandler(value = {LineExistsException.class})
    protected ResponseEntity<ErrorBody> lineExists () {
        return new ResponseEntity(ErrorBody.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("THE LINE ALREADY EXISTS")
                    .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {LineNoExistsException.class})
    protected ResponseEntity<ErrorBody> lineNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_EXTENDED.value())
                .message("THE LINE NO EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {LineRequiredException.class})
    protected ResponseEntity<ErrorBody> lineRequired () {
        return new ResponseEntity(ErrorBody.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("THE LINE IS REQUIRED")
                    .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {LineBadDataException.class})
    protected ResponseEntity<ErrorBody> lineBadData () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE LINE REQUIRES THE NUMBER OR THE LINE_ID")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
