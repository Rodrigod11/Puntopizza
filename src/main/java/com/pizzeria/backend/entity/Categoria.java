package com.pizzeria.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity @Table(name="categoria") // Clase entidad mapeada a la tabla "categoria"
public class Categoria {

  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) // PK con autoincremento
  private Long id;

  @NotBlank // No puede estar vacío
  @Column(length=80, nullable=false, unique=true) // Máx 80 caracteres, obligatorio y único
  private String nombre;

  @Column(length=255) // Campo opcional, máximo 255 caracteres
  private String descripcion;

  @Column(nullable=false) // Campo obligatorio
  private Boolean activo = true; // Valor por defecto: true

  // Métodos getter y setter
  public Long getId(){return id;} public void setId(Long id){this.id=id;} // Acceso y modificación de id
  public String getNombre(){return nombre;} public void setNombre(String nombre){this.nombre=nombre;} // Acceso y modificación de nombre
  public String getDescripcion(){return descripcion;} public void setDescripcion(String descripcion){this.descripcion=descripcion;} // Acceso y modificación de descripción
  public Boolean getActivo(){return activo;} public void setActivo(Boolean activo){this.activo=activo;} // Acceso y modificación de activo
}
