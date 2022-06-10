package com.utn.UTNPhones.controller.advice;


import com.utn.UTNPhones.exceptions.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class MySQLControllerAdvice {

    @ExceptionHandler(value = {SQLException.class})
    protected ResponseEntity<ErrorBody> sqlException (SQLException exception) {
        String errorMsg = exception.getMessage();
        if (errorMsg == null)
            errorMsg = exception.toString();
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(errorMsg)
                .build()
                ,HttpStatus.NOT_FOUND
        );
    }

}
