package com.ventas.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ventas.app.entity.DetalleVenta;
import com.ventas.app.entity.embeddable.DetalleVentaKey;

public interface DetalleVentaJpaRepository extends JpaRepository<DetalleVenta, DetalleVentaKey> {

}
