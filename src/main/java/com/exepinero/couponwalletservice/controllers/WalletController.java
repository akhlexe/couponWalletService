package com.exepinero.couponwalletservice.controllers;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.WalletNotFoundException;
import com.exepinero.couponwalletservice.hateoas.WalletModelAssembler;
import com.exepinero.couponwalletservice.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletRepository walletRepository;
    private final WalletModelAssembler assembler;

    @Autowired
    public WalletController(WalletRepository walletRepository, WalletModelAssembler assembler) {
        this.walletRepository = walletRepository;
        this.assembler = assembler;
    }


    @GetMapping
    public CollectionModel<EntityModel<Wallet>> getWallets(){

        List<EntityModel<Wallet>> wallets = walletRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(wallets);
    }

    @GetMapping("/{id}")
    public EntityModel<Wallet> getWallet(@PathVariable("id") Integer id){

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        return assembler.toModel(wallet);
    }


    @PostMapping
    public ResponseEntity<?> createWallet(@RequestBody Wallet newWallet){
        EntityModel<Wallet> entityModel = assembler.toModel(walletRepository.save(newWallet));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/id")
    public void deleteWallet(@PathVariable Integer id){

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        walletRepository.delete(wallet);
    }

}





















