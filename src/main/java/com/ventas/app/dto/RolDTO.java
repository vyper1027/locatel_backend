package com.ventas.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nombre")
    private String nombre;

	@JsonProperty("descipcion")
	private String descripcion;
	
}
