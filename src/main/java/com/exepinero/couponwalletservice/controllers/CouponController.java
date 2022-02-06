package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.CouponNotFoundException;
import com.exepinero.couponwalletservice.exceptions.WalletNotFoundException;
import com.exepinero.couponwalletservice.hateoas.CouponModelAssembler;
import com.exepinero.couponwalletservice.repositories.CouponRepository;
import com.exepinero.couponwalletservice.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallets/{walletId}/coupons")
public class CouponController {

    private final WalletRepository walletRepository;
    private final CouponRepository couponRepository;
    private final CouponModelAssembler couponModelAssembler;


    @Autowired
    public CouponController(WalletRepository walletRepository, CouponRepository couponRepository, CouponModelAssembler couponModelAssembler) {
        this.walletRepository = walletRepository;
        this.couponRepository = couponRepository;
        this.couponModelAssembler = couponModelAssembler;
    }


    @GetMapping
    public List<Coupon> getCoupons(@PathVariable("walletId") Integer walletId){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return wallet.getCupones();
    }

    @GetMapping("{id}")
    public EntityModel<Coupon> getOne(@PathVariable Integer id){
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        return couponModelAssembler.toModel(coupon);
    }

    @PostMapping
    public EntityModel<Coupon> createCoupon(@PathVariable("walletId") Integer walletId, @RequestBody Coupon coupon){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        coupon.setWalletContenedora(wallet);
        return couponModelAssembler.toModel(couponRepository.save(coupon));
    }

    @DeleteMapping("/id")
    public void deleteCoupon(@PathVariable("id") Integer id){
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
        couponRepository.delete(coupon);
    }
}
