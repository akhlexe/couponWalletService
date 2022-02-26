package com.exepinero.couponwalletservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
            WalletNotFoundException.class,
            CouponNotFoundException.class,
            UsernameNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e){

        // 1. Create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getCause(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        // 2. Return respons entity
        return new ResponseEntity<>(apiException, notFound);
    }
}
