package com.pizzeria.backend.entity;

public enum PedidoEstado {
    // Para compatibilidad con datos antiguos en DB
    PENDIENTE,
    NUEVO,
    PAGADO,
    ANULADO
}
