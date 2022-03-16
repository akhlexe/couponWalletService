package com.exepinero.couponwalletservice.services;

import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.hateoas.CouponModelAssembler;
import com.exepinero.couponwalletservice.repositories.CouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    // -------------- VAR DECLARATION --------------------------

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponModelAssembler assembler;
    @Mock
    private WalletService walletService;

    private CouponService underTest;

    private AutoCloseable autoCloseable;


    // ------------------ BEFORE EACH -------------------------

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CouponService(
                couponRepository,
                assembler,
                walletService
        );
    }



    @Test
    void canGetCoupons() {
        // given
        int expectedWalletId = 1;
        EntityModel<Wallet> testWalletWithCoupons = this.createTestWalletWithCoupons();
        when(walletService.getWalletById(expectedWalletId)).thenReturn(testWalletWithCoupons);

        // when
        underTest.getCoupons(expectedWalletId);

        // then
        verify(walletService,times(1)).getWalletById(expectedWalletId);
        verify(assembler, times(3)).toModel(any());
    }

    @Test
    @Disabled
    void canGetOne() {
    }

    @Test
    @Disabled
    void canCreateCoupon() {
    }

    @Test
    @Disabled
    void canDeleteCoupon() {
    }


    // ------------------ AFTER EACH -------------------------

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    // ------------------ UTIL METHODS ----------------------

    /*
    * Creates a dummy test wallet with coupons for testing purpose.
     */

    private EntityModel<Wallet> createTestWalletWithCoupons(){
        Wallet testWallet = new Wallet();
        testWallet.setId(1);
        testWallet.setFechaCreacion(new Date());
        testWallet.setWalletOwner("Exequiel");

        Coupon cupon1 = new Coupon();
        cupon1.setEmpresa("Netflix");
        cupon1.setPromo("1 mes gratis");
        cupon1.setFechaExpiracion(new Date());

        Coupon cupon2 = new Coupon();
        cupon2.setEmpresa("Mc Donalds");
        cupon2.setPromo("Hamburguesa Gratis!");
        cupon2.setFechaExpiracion(new Date());

        Coupon cupon3 = new Coupon();
        cupon3.setEmpresa("Coca cola");
        cupon3.setPromo("Gasosea gratis");
        cupon3.setFechaExpiracion(new Date());

        testWallet.addCouponToWallet(cupon1);
        testWallet.addCouponToWallet(cupon2);
        testWallet.addCouponToWallet(cupon3);

        cupon1.setWalletContenedora(testWallet);
        cupon2.setWalletContenedora(testWallet);
        cupon3.setWalletContenedora(testWallet);

        return EntityModel.of(testWallet);
    }

    /*
    * Creates a dummy test coupon only for testing purpose
     */
    private Optional<Coupon> createTestCoupon(){

        Coupon cupon1 = new Coupon();
        cupon1.setEmpresa("Netflix");
        cupon1.setPromo("1 mes gratis");
        cupon1.setFechaExpiracion(new Date());

        return Optional.of(cupon1);
    }
}