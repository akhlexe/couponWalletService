package com.exepinero.couponwalletservice.services;


import com.exepinero.couponwalletservice.controllers.WalletController;
import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.CouponNotFoundException;
import com.exepinero.couponwalletservice.hateoas.CouponModelAssembler;
import com.exepinero.couponwalletservice.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponModelAssembler couponModelAssembler;
    private final WalletService walletService;

    @Autowired
    public CouponService(CouponRepository couponRepository, CouponModelAssembler couponModelAssembler, WalletService walletService) {
        this.couponRepository = couponRepository;
        this.couponModelAssembler = couponModelAssembler;
        this.walletService = walletService;
    }


    public CollectionModel<EntityModel<Coupon>> getCoupons(Integer walletId){
        EntityModel<Wallet> walletById = walletService.getWalletById(walletId);
        Wallet wallet = walletById.getContent();

        List<EntityModel<Coupon>> couponModels = wallet.getCupones().stream()
                .map(couponModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(couponModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WalletController.class).getWallet(walletId)).withRel("wallet"));
    }

    public EntityModel<Coupon> getOne(Integer couponId){
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        return couponModelAssembler.toModel(coupon);
    }

    public EntityModel<Coupon> createCoupon(Integer walletId, Coupon coupon){

        EntityModel<Wallet> entityWallet = walletService.getWalletById(walletId);
        Wallet wallet = entityWallet.getContent();

        coupon.setWalletContenedora(wallet);
        return couponModelAssembler.toModel(couponRepository.save(coupon));
    }

    public void deleteCoupon(Integer id){
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        couponRepository.delete(coupon);
    }


}


















