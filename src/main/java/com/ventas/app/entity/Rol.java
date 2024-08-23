package com.ventas.app.entity;

import java.io.Serializable;

import com.ventas.app.enums.RolEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Rol  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RolEnum nombre;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

}
