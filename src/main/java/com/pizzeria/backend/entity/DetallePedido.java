package com.pizzeria.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;   // Evita mostrar datos en JSON
import com.fasterxml.jackson.annotation.JsonProperty; // Permite exponer propiedades personalizadas en JSON
import jakarta.persistence.*; // Anotaciones de JPA (para mapear la BD)
import jakarta.validation.constraints.Min; // Validación: valor mínimo
import java.math.BigDecimal; // Tipo de dato para valores monetarios

@Entity
@Table(name = "detalle_pedido") // Mapea a la tabla "detalle_pedido"
public class DetallePedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // PK autoincremental
  private Long id;

  @JsonIgnore // No incluir el objeto Pedido completo en el JSON
  @ManyToOne(optional = false) // Muchos detalles pertenecen a un pedido
  @JoinColumn(name = "pedido_id") // FK hacia la tabla pedido
  private Pedido pedido;

  @JsonIgnore // No incluir el objeto Producto completo en el JSON
  @ManyToOne(optional = false) // Muchos detalles hacen referencia a un producto
  @JoinColumn(name = "producto_id") // FK hacia la tabla producto
  private Producto producto;

  @Min(1) // La cantidad mínima es 1
  @Column(nullable = false) // Obligatorio
  private Integer cantidad = 1;

  @Column(nullable = false)
  private BigDecimal precioUnitario;   // Precio al momento de la compra (snapshot)

  @Column(nullable = false)
  private BigDecimal subtotal;         // cantidad * precioUnitario

  // === Getters personalizados para la respuesta JSON ===
  @JsonProperty("productoId") // Devuelve solo el id del producto en el JSON
  public Long getProductoId() {
    return (producto != null) ? producto.getId() : null;
  }

  @JsonProperty("productoNombre") // Devuelve solo el nombre del producto en el JSON
  public String getProductoNombre() {
    return (producto != null) ? producto.getNombre() : null;
  }

  // === Getters/Setters estándar ===
  public Long getId() { return id; } 
  public void setId(Long id) { this.id = id; }

  public Pedido getPedido() { return pedido; } 
  public void setPedido(Pedido pedido) { this.pedido = pedido; }

  public Producto getProducto() { return producto; } 
  public void setProducto(Producto producto) { this.producto = producto; }

  public Integer getCantidad() { return cantidad; } 
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

  public BigDecimal getPrecioUnitario() { return precioUnitario; } 
  public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

  public BigDecimal getSubtotal() { return subtotal; } 
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
