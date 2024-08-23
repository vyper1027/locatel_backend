package com.ventas.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ventas.app.entity.Usuario;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, String> {

	@Query(value = "SELECT u.cedula, u,nombre, u.email, u.contraseña, u.direccion, u.telefono, u.rol_id  FROM usuarios u WHERE u.email = :email", nativeQuery = true)
	Usuario buscarUsuarioPorEmail(@Param("email") String email) throws Exception;
}
