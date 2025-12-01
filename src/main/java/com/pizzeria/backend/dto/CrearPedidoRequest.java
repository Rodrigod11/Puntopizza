package com.pizzeria.backend.dto;

public class CrearPedidoRequest {
    private Long mesaId;
    private Long clienteId; // opcional

    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}
