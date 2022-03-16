package com.exepinero.couponwalletservice.services;

import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.CouponNotFoundException;
import com.exepinero.couponwalletservice.hateoas.CouponModelAssembler;
import com.exepinero.couponwalletservice.repositories.CouponRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    // -------------- VAR DECLARATION --------------------------

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponModelAssembler couponModelAssembler;
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
                couponModelAssembler,
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
        verify(couponModelAssembler, times(3)).toModel(any(Coupon.class));
    }

    @Test
    void canGetOne() {
        // given
        int expectedCouponId = 1;
        Optional<Coupon> testCoupon = this.createTestCoupon();
        when(couponRepository.findById(expectedCouponId)).thenReturn(testCoupon);

        // when
        underTest.getOne(expectedCouponId);

        // then
        verify(couponRepository,times(1)).findById(expectedCouponId);
        verify(couponModelAssembler, times(1)).toModel(any(Coupon.class));
    }

    @Test
    void getOneThrowsExceptionWhenCouponIdNotFound(){

        // given
        int idInexistence = 30;
        when(couponRepository.findById(idInexistence))
                .thenThrow(CouponNotFoundException.class);

        // then
        Assertions.assertThrows(CouponNotFoundException.class,
                () -> underTest.getOne(idInexistence));

        Assertions.assertThrows(CouponNotFoundException.class,
                () -> underTest.deleteCoupon(idInexistence));
    }

    @Test
    void canCreateCoupon() {
        // given
        int expectedWalletId = 1;
        Coupon coupon = createTestCoupon().get();
        EntityModel<Wallet> testWalletWithCoupons = createTestWalletWithCoupons();
        when(walletService.getWalletById(any(Integer.class))).thenReturn(testWalletWithCoupons);
        when(couponRepository.save(coupon)).thenReturn(coupon);

        // when
        underTest.createCoupon(expectedWalletId,coupon);

        // then
        verify(walletService, times(1)).getWalletById(expectedWalletId);
        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(couponModelAssembler, times(1)).toModel(any(Coupon.class));
    }

    @Test
    void canDeleteCoupon() {

        // given
        int expectedId = 1;
        Optional<Coupon> testCoupon = createTestCoupon();
        when(couponRepository.findById(expectedId)).thenReturn(testCoupon);

        // when
        underTest.deleteCoupon(expectedId);

        // then
        verify(couponRepository, times(1)).findById(expectedId);
        verify(couponRepository, times(1)).delete(testCoupon.get());
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
        cupon1.setId(2);
        cupon1.setEmpresa("Netflix");
        cupon1.setPromo("1 mes gratis");
        cupon1.setFechaExpiracion(new Date());

        Coupon cupon2 = new Coupon();
        cupon1.setId(3);
        cupon2.setEmpresa("Mc Donalds");
        cupon2.setPromo("Hamburguesa Gratis!");
        cupon2.setFechaExpiracion(new Date());

        Coupon cupon3 = new Coupon();
        cupon1.setId(4);
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
        cupon1.setId(1);
        cupon1.setEmpresa("Netflix");
        cupon1.setPromo("1 mes gratis");
        cupon1.setFechaExpiracion(new Date());

        return Optional.of(cupon1);
    }
}