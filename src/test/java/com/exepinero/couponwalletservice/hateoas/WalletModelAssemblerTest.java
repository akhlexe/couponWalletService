package com.exepinero.couponwalletservice.hateoas;

import com.exepinero.couponwalletservice.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WalletModelAssemblerTest {

    private WalletModelAssembler underTest;

    @BeforeEach
    void setUp() {
        underTest = new WalletModelAssembler();
    }

    // TODO terminar test de model assembler

    @Test
    @Disabled
    void IsToModelWorkingProperly() {

        Wallet testWallet = createTestWallet();

    }

    /*
    *
     */
    private Wallet createTestWallet(){
        Wallet testWallet = new Wallet();
        testWallet.setId(1);
        testWallet.setWalletOwner("Exequiel");
        testWallet.setFechaCreacion(new Date());

        return testWallet;
    }
}