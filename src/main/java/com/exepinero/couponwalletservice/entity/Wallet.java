package com.exepinero.couponwalletservice.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Wallet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date fechaCreacion;

    @NotNull
    private String walletOwner;

    @OneToMany(mappedBy = "walletContenedora",cascade = CascadeType.ALL)
    private List<Coupon> cupones = new ArrayList<>();

    public Wallet() {
    }


    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", walletOwner='" + walletOwner + '\'' +
                ", cupones=" + cupones +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(String walletOwner) {
        this.walletOwner = walletOwner;
    }

    public List<Coupon> getCupones() {
        return cupones;
    }

    public void setCupones(List<Coupon> cupones) {
        this.cupones = cupones;
    }

    public void addCouponToWallet(Coupon coupon){
        this.cupones.add(coupon);
    }
}
