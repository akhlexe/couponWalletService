package com.exepinero.couponwalletservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @NotNull
    private String empresa;
    @NotNull
    private String promo;

    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;

    @ManyToOne
    @JsonIgnore
    private Wallet walletContenedora;


    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", empresa='" + empresa + '\'' +
                ", promo='" + promo + '\'' +
                ", fechaExpiracion=" + fechaExpiracion +
                '}';
    }

    public Wallet getWalletContenedora() {
        return walletContenedora;
    }

    public void setWalletContenedora(Wallet walletContenedora) {
        this.walletContenedora = walletContenedora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
