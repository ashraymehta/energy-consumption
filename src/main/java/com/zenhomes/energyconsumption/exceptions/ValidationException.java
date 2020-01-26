package com.zenhomes.energyconsumption.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends javax.validation.ValidationException {

    public ValidationException() {
    }

    public ValidationException(Throwable throwable) {
        super(throwable);
    }

}