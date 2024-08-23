package com.ventas.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

	@JsonProperty("cedula")
	private String cedula;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("direccion")
	private String direccion;
	
	@JsonProperty("telefono")
	private String telefono;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("rol")
	private String rol;
	
}
