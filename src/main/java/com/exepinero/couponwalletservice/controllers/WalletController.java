package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    // TODO Refactoring a services
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping
    public CollectionModel<EntityModel<Wallet>> getWallets(){

        CollectionModel<EntityModel<Wallet>> wallets = walletService.getWallets();

        return CollectionModel.of(wallets);
    }

    @GetMapping("/{id}")
    public EntityModel<Wallet> getWallet(@PathVariable("id") Integer id){
        return walletService.getWalletById(id);
    }


    @PostMapping
    public ResponseEntity<?> createWallet(@RequestBody Wallet newWallet){
        EntityModel<Wallet> wallet = walletService.createWallet(newWallet);

        return ResponseEntity
                .created(wallet.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(wallet);
    }

    @DeleteMapping("/id")
    public void deleteWallet(@PathVariable Integer id){
        walletService.deleteWallet(id);
    }

}





















