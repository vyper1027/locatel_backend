package com.ventas.app.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDTO {

	@JsonProperty("codigo")
	private Long codigo;
	
	@JsonProperty("fecha")
	private Timestamp fecha;
	
	@JsonProperty("totalVenta")
	private Double totalVenta;
	
	@JsonProperty("cliente_cedula")
	private String clienteCedula;
	
}
