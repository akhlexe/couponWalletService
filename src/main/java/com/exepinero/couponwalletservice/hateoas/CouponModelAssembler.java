package com.exepinero.couponwalletservice.hateoas;

import com.exepinero.couponwalletservice.controllers.CouponController;
import com.exepinero.couponwalletservice.controllers.WalletController;
import com.exepinero.couponwalletservice.entity.Coupon;
import com.exepinero.couponwalletservice.entity.Wallet;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CouponModelAssembler implements RepresentationModelAssembler<Coupon, EntityModel<Coupon>> {

    @Override
    public EntityModel<Coupon> toModel(Coupon entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CouponController.class).getOne(entity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CouponController.class).getCoupons(entity.getWalletContenedora().getId())).withRel("coupons"));
    }
}
