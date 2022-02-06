package com.exepinero.couponwalletservice.exceptions;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(Integer id) {
        super("No existe cupón con ID: "+ id);
    }
}
