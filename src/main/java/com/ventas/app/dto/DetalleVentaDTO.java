package com.ventas.app.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaDTO {

	@JsonProperty("codigo")
	private DetalleVentaKeyDTO codigo;
	
	@JsonProperty("cantidad_producto")
	private Timestamp cantidadProducto;
	
	@JsonProperty("valor_total_producto")
	private Double valorTotalProducto;
	
	@JsonProperty("iva_total")
	private Double ivaTotal;
	
	@JsonProperty("total")
	private Double total;
	
	@JsonProperty("producto_codigo")
	private Long productoCodigo;
	
	@JsonProperty("venta_codigo")
	private Long ventaCodigo;
	
}
