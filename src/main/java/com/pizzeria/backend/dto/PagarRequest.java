package com.pizzeria.backend.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.pizzeria.backend.entity.MetodoPago;
import java.math.BigDecimal;

public class PagarRequest {
    private BigDecimal monto;
    private MetodoPago metodo;

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public MetodoPago getMetodo() { return metodo; }

    @JsonSetter("metodo")
    public void setMetodo(String metodo) {
        this.metodo = (metodo == null) ? null : MetodoPago.valueOf(metodo.trim().toUpperCase());
    }
}
