package com.ventas.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ventas.app.entity.Venta;

public interface VentaJpaRepository extends JpaRepository<Venta, Long> {

}
