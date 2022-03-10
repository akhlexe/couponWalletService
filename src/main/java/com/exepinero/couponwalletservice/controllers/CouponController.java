package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/{walletId}/coupons")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Coupon>> getCoupons(@PathVariable("walletId") Integer walletId){

        return couponService.getCoupons(walletId);
    }

    @GetMapping("{id}")
    public EntityModel<Coupon> getOne(@PathVariable("walletId") Integer walletId, @PathVariable Integer id){

        // TODO chequear si el walletID corresponde al walletID del principal
        // TODO Probablemente se puede agregar un filtro al request que para las URL de /wallets verifique siempre
        // TODO que el principal sea el correcto
        return couponService.getOne(id);
    }

    @PostMapping
    public EntityModel<Coupon> createCoupon(@PathVariable("walletId") Integer walletId, @RequestBody Coupon coupon){
        return couponService.createCoupon(walletId,coupon);
    }

    @DeleteMapping("/id")
    public void deleteCoupon(@PathVariable("id") Integer id){
        couponService.deleteCoupon(id);
    }
}
