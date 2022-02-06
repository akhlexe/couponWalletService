package com.exepinero.couponwalletservice;

import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.repositories.CouponRepository;
import com.exepinero.couponwalletservice.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class CargandoTestData implements CommandLineRunner {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {

        Wallet tempWallet = new Wallet();
        tempWallet.setWalletOwner("Exequiel");
        tempWallet.setFechaCreacion(new Date());

        Coupon cupon1 = new Coupon();
        cupon1.setEmpresa("Netflix");
        cupon1.setPromo("1 mes gratis");
        cupon1.setFechaExpiracion(new Date());

        Coupon cupon2 = new Coupon();
        cupon2.setEmpresa("Mc Donalds");
        cupon2.setPromo("Hamburguesa Gratis!");
        cupon2.setFechaExpiracion(new Date());


        tempWallet.addCouponToWallet(cupon1);
        tempWallet.addCouponToWallet(cupon2);
        cupon1.setWalletContenedora(tempWallet);
        cupon2.setWalletContenedora(tempWallet);

        walletRepository.save(tempWallet);
        couponRepository.save(cupon1);
        couponRepository.save(cupon2);

        System.out.println("################################## Después de guardar los 2 primeros cupones");

        Coupon cupon3 = new Coupon();
        cupon3.setEmpresa("Coca cola");
        cupon3.setPromo("Gasosea gratis");
        cupon3.setFechaExpiracion(new Date());

        cupon3.setWalletContenedora(tempWallet);

        couponRepository.save(cupon3);

        System.out.println("################################## Después de guardar el último cupón");

        System.out.println("FINISH");
    }
}
