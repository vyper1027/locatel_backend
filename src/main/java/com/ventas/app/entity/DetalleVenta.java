package com.ventas.app.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ventas.app.entity.embeddable.DetalleVentaKey;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detalles_ventas")
@Getter
@Setter
public class DetalleVenta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	DetalleVentaKey codigo;
	
	@Column(name = "cantidad_producto", nullable = false)
	private Timestamp cantidadProducto;
	
	@Column(name = "valor_total_producto", nullable = false)
	private Double valorTotalProducto;
	
	@Column(name = "iva_total", nullable = false)
	private Double ivaTotal;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("productoCodigo")
	@JoinColumn(name = "producto_codigo")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("ventaCodigo")
	@JoinColumn(name = "venta_codigo")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Venta venta;
}