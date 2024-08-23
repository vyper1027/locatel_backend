package com.ventas.app.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;

import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter 
@Setter
public class DetalleVentaKey implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	@Column(name = "producto_codigo")
	private Long productoCodigo;
	
	@Column(name = "venta_codigo")
	private Long ventaCodigo;

}
