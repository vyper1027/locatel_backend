package com.ventas.app.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.PrePersist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ventas")
@Getter
@Setter
public class Venta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long codigo;
	
	@Column(name = "fecha", nullable = false)
	private Timestamp fecha;
	
	@Column(name = "total_venta", nullable = false)
	private Double totalVenta;
	
	@PrePersist
	public void prePersist(){
		Date date = new Date();
		fecha = new Timestamp(date.getTime());
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_cedula")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private Usuario cliente;
}
