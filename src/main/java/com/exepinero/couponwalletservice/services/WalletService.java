package com.exepinero.couponwalletservice.services;

import com.exepinero.couponwalletservice.entity.Wallet;
import com.exepinero.couponwalletservice.exceptions.WalletNotFoundException;
import com.exepinero.couponwalletservice.hateoas.WalletModelAssembler;
import com.exepinero.couponwalletservice.repositories.WalletRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {


    private final WalletRepository walletRepository;
    private final WalletModelAssembler assembler;

    public WalletService(WalletRepository walletRepository, WalletModelAssembler assembler) {
        this.walletRepository = walletRepository;
        this.assembler = assembler;
    }


    public CollectionModel<EntityModel<Wallet>> getWallets(){

        List<EntityModel<Wallet>> wallets = walletRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(wallets);
    }

    public EntityModel<Wallet> getWalletById(Integer id){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        return assembler.toModel(wallet);
    }

    public EntityModel<Wallet> createWallet(Wallet wallet){
        Wallet walletSaved = walletRepository.save(wallet);
        EntityModel<Wallet> walletEntityModel = assembler.toModel(walletSaved);
        return walletEntityModel;
    }

    public void deleteWallet(Integer id){
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        walletRepository.delete(wallet);
    }
}




















