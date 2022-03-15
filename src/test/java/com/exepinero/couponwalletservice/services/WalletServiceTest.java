package com.exepinero.couponwalletservice.services;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.WalletNotFoundException;
import com.exepinero.couponwalletservice.hateoas.WalletModelAssembler;
import com.exepinero.couponwalletservice.repositories.WalletRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletModelAssembler walletModelAssembler;
    private WalletService underTest;
    private AutoCloseable autoCloseable;

    // ----------- SET UP --------------------------------------------

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new WalletService(walletRepository,walletModelAssembler);
    }


    @Test
    void canGetAllWallets() {

        // given
        Wallet testingWallet = new Wallet();
        testingWallet.setId(1);
        testingWallet.setWalletOwner("Exequiel");
        testingWallet.setFechaCreacion(new Date());

        when(walletRepository.findAll()).thenReturn(Lists.newArrayList(testingWallet));
        when(walletModelAssembler.toModel(any())).thenReturn(EntityModel.of(testingWallet));

        underTest = new WalletService(walletRepository,walletModelAssembler);

        // when
        CollectionModel<EntityModel<Wallet>> wallets = underTest.getWallets();

        //assert
        Mockito.verify(walletRepository, times(1)).findAll();
        Mockito.verify(walletModelAssembler, times(1)).toModel(any());

    }

    @Test
    void canGetWalletById() {
        // given
        Wallet testWallet = this.createWallet();
        Optional<Wallet> optionalWallet = Optional.of(testWallet);

        when(walletRepository.findById(1)).thenReturn(optionalWallet);
        when(walletModelAssembler.toModel(testWallet)).thenReturn(EntityModel.of(testWallet));

        // when
        EntityModel<Wallet> walletById = underTest.getWalletById(1);

        // then
        verify(walletRepository).findById(1);
        verify(walletModelAssembler).toModel(testWallet);
    }

    @Test
    void canCreateWallet() {
        // given
        Wallet wallet = createWallet();
        when(walletRepository.save(wallet)).thenReturn(wallet);

        // when
        underTest.createWallet(wallet);

        // then
        verify(walletRepository, times(1)).save(wallet);
        verify(walletModelAssembler, times(1)).toModel(wallet);
    }

    @Test
    void deleteWallet() {
        // given
        int idToDelete = 1;
        Wallet wallet = createWallet();
        when(walletRepository.findById(1)).thenReturn(Optional.of(wallet));

        // when
        underTest.deleteWallet(idToDelete);

        // then
        verify(walletRepository,times(1)).findById(1);
        verify(walletRepository,times(1)).delete(wallet);

    }

    @Test
    void findByIdThrowExceptionWhenWalletNotFound(){
        //given
        int idInexistente = 30;
        when(walletRepository.findById(idInexistente))
                .thenThrow(WalletNotFoundException.class);

        // then
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> underTest.getWalletById(idInexistente));
    }

    // --------------------- AFTER EACH -----------------------------
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    // ------------------ UTILS METHODS ------------------------------
    public Wallet createWallet(){
        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setWalletOwner("Exequiel");
        wallet.setFechaCreacion(new Date());
        wallet.setCupones(Lists.newArrayList());

        return wallet;
    }
}