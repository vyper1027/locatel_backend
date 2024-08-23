package com.ventas.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ventas.app.entity.Rol;

public interface RolJpaRepository extends JpaRepository<Rol, Long> {

	@Query(value = "SELECT r.id, r.nombre, r.descripcion FROM roles r WHERE r.nombre = :nombre", nativeQuery = true)
	Rol buscarRolPorNombre(@Param("nombre") String nombre) throws Exception;
}
