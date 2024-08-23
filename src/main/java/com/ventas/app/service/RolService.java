package com.ventas.app.service;

import org.springframework.data.domain.Page;

import com.ventas.app.entity.Rol;


public interface RolService {
	
	Rol crearRol(Rol rol) throws Exception;
	
	void eliminarRolPorId(Long id) throws Exception;
	
	Rol buscarRolPorId(Long id) throws Exception;
	
	Page<Rol> filtrarRolPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc) throws Exception;
	
	Rol buscarRolPorNombre(String nombre) throws Exception;

}
