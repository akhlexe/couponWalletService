package com.exepinero.couponwalletservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* Controller para las entradas publicas dando detalle de como logear y obtener la información
* de las wallets y cupones
 */

@RestController
@RequestMapping(path = {"/","/home"})
public class WelcomeController {

    @GetMapping
    public String getHome(){

        String infoInicial = "Para acceder a la información de wallets acceder \n" +
                                     "al url /login pasando en el body un objeto JSON \n" +
                                     "con campos de 'username' y 'password'\n";

        return infoInicial;
    }
}
