package com.pizzeria.backend.entity;

import jakarta.persistence.*; // Anotaciones para mapear esta clase a una tabla en la BD
import jakarta.validation.constraints.Email; // Validación: formato válido de email
import jakarta.validation.constraints.NotBlank; // Validación: campo no vacío

@Entity @Table(name="cliente") // Clase mapeada a la tabla "cliente"
public class Cliente {

  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) // PK con autoincremento
  private Long id;

  @NotBlank @Column(length=120, nullable=false) // Obligatorio, máx 120 caracteres
  private String nombre;

  @Column(length=20) // Opcional, máx 20 caracteres
  private String telefono;

  @Email @Column(length=120) // Validación de email, máx 120 caracteres
  private String email;

  @Column(length=255) // Opcional, máx 255 caracteres
  private String direccion;

  // Métodos getter y setter para acceder/modificar los atributos
  public Long getId(){return id;} public void setId(Long id){this.id=id;} // Acceso y modificación del id
  public String getNombre(){return nombre;} public void setNombre(String nombre){this.nombre=nombre;} // Acceso y modificación del nombre
  public String getTelefono(){return telefono;} public void setTelefono(String telefono){this.telefono=telefono;} // Acceso y modificación del teléfono
  public String getEmail(){return email;} public void setEmail(String email){this.email=email;} // Acceso y modificación del email
  public String getDireccion(){return direccion;} public void setDireccion(String direccion){this.direccion=direccion;} // Acceso y modificación de la dirección
}
