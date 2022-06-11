package com.utn.UTNPhones.controller.advice;

import com.utn.UTNPhones.exceptions.ErrorBody;
import com.utn.UTNPhones.exceptions.PhonelineNotExistsException;
import com.utn.UTNPhones.exceptions.ProvinceIsRequiredException;
import com.utn.UTNPhones.exceptions.ProvinceNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProvinceControllerAdvice {

    @ExceptionHandler(value = {ProvinceIsRequiredException.class})
    protected ResponseEntity<ErrorBody> provinceRequired () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("THE PROVINCE IS REQUIRED")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {ProvinceNotExistsException.class})
    protected ResponseEntity<ErrorBody> provinceNotExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_EXTENDED.value())
                .message("THE PROVINCE DOES NOT EXISTS")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

}
