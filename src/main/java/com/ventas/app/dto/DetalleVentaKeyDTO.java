package com.ventas.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaKeyDTO {

	@JsonProperty("producto_codigo")
	private Long productoCodigo;
	
	@JsonProperty("venta_codigo")
	private Long ventaCodigo;
}
