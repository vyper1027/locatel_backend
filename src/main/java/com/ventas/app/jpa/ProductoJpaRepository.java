package com.ventas.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ventas.app.entity.Producto;

public interface ProductoJpaRepository extends JpaRepository<Producto, Long> {

}
