package com.ventas.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
	
	@JsonProperty("codigo")
	private Long codigo;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("valor")
	private Double valor;
	
	@JsonProperty("tieneIva")
	private boolean tieneIva;
	
	@JsonProperty("iva")
	private Double iva;

}
