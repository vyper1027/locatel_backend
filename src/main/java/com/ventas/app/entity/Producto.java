package com.ventas.app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "tiene_iva", nullable = false)
	private boolean tieneIva;
	
	@Column(name = "iva", nullable = false)
	private Double iva;
}
