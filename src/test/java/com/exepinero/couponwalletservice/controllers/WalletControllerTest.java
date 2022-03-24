package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.services.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = WalletController.class)
class WalletControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;

    @Test
    @Disabled
    void getWallets() {
    }

    @Test
    @Disabled
    void getWallet() throws Exception {

        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setWalletOwner("Exequiel");
        wallet.setFechaCreacion(new Date());

//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/wallets/{userId}", 1)
//                .contentType("application/json")
    }

    @Test
    @Disabled
    void createWallet() {
    }

    @Test
    @Disabled
    void deleteWallet() {
    }
}