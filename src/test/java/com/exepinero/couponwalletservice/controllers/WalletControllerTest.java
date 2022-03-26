package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.services.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {

    @MockBean
    private WalletService walletService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void getWallet() throws Exception {

        //given
        Wallet testingWallet = createTestingWallet();
        int testingId = 1;
        when(walletService.getWalletById(testingId)).thenReturn(EntityModel.of(testingWallet));


        // Request test
        mockMvc.perform(get("/wallets/{id}", testingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaCreacion").isNotEmpty())
                .andExpect(jsonPath("$.cupones").isEmpty())
                .andExpect(jsonPath("$.walletOwner").value("Exequiel"));

    }

    @Test
    void getWallets() throws Exception {

        //given
        CollectionModel<EntityModel<Wallet>> testingCollectionOfWallets = createTestingCollectionOfWallets();
        when(walletService.getWallets()).thenReturn(testingCollectionOfWallets);

        // Request test
        mockMvc.perform(get("/wallets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(walletService).getWallets();

    }

    @Test
    void createWallet() throws Exception {
        //given
        Wallet testingWallet = createTestingWallet();
        EntityModel<Wallet> entityModel = EntityModel.of(testingWallet,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WalletController.class).getWallet(testingWallet.getId())).withSelfRel());

        when(walletService.createWallet(any())).thenReturn(entityModel);

        // Request test
        mockMvc.perform(post("/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testingWallet)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteWallet() throws Exception {

        //given
        Integer testingId = 1;

        // Request test
        mockMvc.perform(delete("/wallets/{id}", testingId))
                .andExpect(status().isOk());
        verify(walletService).deleteWallet(testingId);
    }

    public Wallet createTestingWallet(){
        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setWalletOwner("Exequiel");
        wallet.setFechaCreacion(new Date());
        return wallet;
    }

    public CollectionModel<EntityModel<Wallet>> createTestingCollectionOfWallets(){
        List<Wallet> wallets = new ArrayList<>();

        Wallet wallet1 = new Wallet();
        wallet1.setId(1);
        wallet1.setWalletOwner("Exequiel");
        wallet1.setFechaCreacion(new Date());

        Wallet wallet2 = new Wallet();
        wallet2.setId(2);
        wallet2.setWalletOwner("Nicolás");
        wallet2.setFechaCreacion(new Date());

        Wallet wallet3 = new Wallet();
        wallet3.setId(2);
        wallet3.setWalletOwner("Nicolás");
        wallet3.setFechaCreacion(new Date());

        wallets.add(wallet1);
        wallets.add(wallet2);
        wallets.add(wallet3);

        List<EntityModel<Wallet>> entitys = wallets.stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return CollectionModel.of(entitys);

    }
}