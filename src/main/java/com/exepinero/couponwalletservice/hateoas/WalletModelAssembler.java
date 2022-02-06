package com.exepinero.couponwalletservice.hateoas;

import com.exepinero.couponwalletservice.controllers.CouponController;
import com.exepinero.couponwalletservice.controllers.WalletController;
import com.exepinero.couponwalletservice.entity.Wallet;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class WalletModelAssembler implements RepresentationModelAssembler<Wallet, EntityModel<Wallet>> {

    @Override
    public EntityModel<Wallet> toModel(Wallet entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WalletController.class).getWallet(entity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WalletController.class).getWallets()).withRel("wallets"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CouponController.class).getCoupons(entity.getId())).withRel("coupons"));
    }
}
