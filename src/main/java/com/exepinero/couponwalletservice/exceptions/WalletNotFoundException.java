package com.exepinero.couponwalletservice.exceptions;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Integer id) {
        super("No existe billetera con el id: "+id);
    }
}
