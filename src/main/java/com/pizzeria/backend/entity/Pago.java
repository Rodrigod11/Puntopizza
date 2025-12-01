package com.pizzeria.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @OneToOne(optional = false)
  @JoinColumn(name = "pedido_id", unique = true, nullable = false)
  private Pedido pedido;

  // 👇🏼 Aquí el cambio: de String a ENUM
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private MetodoPago metodo;

  @NotNull
  @DecimalMin("0.0")
  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal monto;

  @Column(nullable = false)
  private LocalDateTime fechaPago = LocalDateTime.now();

  @JsonProperty("pedidoId")
  public Long getPedidoId() { return (pedido != null) ? pedido.getId() : null; }

  // Getters/Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Pedido getPedido() { return pedido; }
  public void setPedido(Pedido pedido) { this.pedido = pedido; }

  public MetodoPago getMetodo() { return metodo; }
  public void setMetodo(MetodoPago metodo) { this.metodo = metodo; }

  public BigDecimal getMonto() { return monto; }
  public void setMonto(BigDecimal monto) { this.monto = monto; }

  public LocalDateTime getFechaPago() { return fechaPago; }
  public void setFechaPago(LocalDateTime f) { this.fechaPago = f; }
}
