package com.ventas.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("contrasenia")
	private String contrasenia;
}
