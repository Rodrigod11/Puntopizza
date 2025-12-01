package com.pizzeria.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mesa")
public class Mesa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "El número de mesa es obligatorio")
  @Min(value = 1, message = "El número de mesa debe ser >= 1")
  @Column(nullable = false, unique = true)
  private Integer numero;

  @NotNull(message = "La capacidad es obligatoria")
  @Min(value = 1, message = "La capacidad debe ser >= 1")
  @Column(nullable = false)
  private Integer capacidad;

  @Column(nullable = false)
  private Boolean ocupada = false;

  // Getters/Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Integer getNumero() { return numero; }
  public void setNumero(Integer numero) { this.numero = numero; }
  public Integer getCapacidad() { return capacidad; }
  public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
  public Boolean getOcupada() { return ocupada; }
  public void setOcupada(Boolean ocupada) { this.ocupada = ocupada; }
}
