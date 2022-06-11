package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PhonelineControllerAdvice {

    @ExceptionHandler(value = {PhonelineExistsException.class})
    protected ResponseEntity<ErrorBody> phonelineExists () {
        return new ResponseEntity(ErrorBody.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("THE LINE ALREADY EXISTS")
                    .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {PhonelineNotExistsException.class})
    protected ResponseEntity<ErrorBody> phonelineNotExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_EXTENDED.value())
                .message("THE LINE DOES NOT EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {PhonelineAsignedException.class})
    protected ResponseEntity<ErrorBody> phonelineAlreadyAsigned () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("THE LINE IS ALREADY ASSIGNED")
                .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {PhonelineRequiredException.class})
    protected ResponseEntity<ErrorBody> phonelineRequired () {
        return new ResponseEntity(ErrorBody.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("THE LINE IS REQUIRED")
                    .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {PhonelineBadDataException.class})
    protected ResponseEntity<ErrorBody> phonelineBadData () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE LINE REQUIRES THE NUMBER OR THE LINE_ID")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {PhonelineEqualException.class})
    protected ResponseEntity<ErrorBody> phonelineEqual() {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("CAN NOT CALL THE SAME LINE")
                .build()
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {PhonelineOriginLowException.class})
    protected ResponseEntity<ErrorBody> phonelineOriginLow() {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("YOUR LINE IS DISCONTINUED")
                .build()
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {PhonelineDestinationLowException.class})
    protected ResponseEntity<ErrorBody> phonelineDestinationLow() {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("THE DESTINATION LINE IS DISCONTINUED")
                .build()
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {PhonelineAssociatedCallsException.class})
    protected ResponseEntity<ErrorBody> phonelineHasAssociateCalls() {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("THE LINE HAS ASSOCIATED CALL/S")
                .build()
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = {PhonelineLengthException.class})
    protected ResponseEntity<ErrorBody> phonelineBadLength () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE NUMBER MUST BE 10 CHARACTERS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

}
