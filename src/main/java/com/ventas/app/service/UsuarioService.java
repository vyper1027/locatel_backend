package com.ventas.app.service;

import org.springframework.data.domain.Page;

import com.ventas.app.entity.Usuario;

public interface UsuarioService {

	Usuario crearUsuario(Usuario usuario) throws Exception;
	
	void eliminarUsuarioPorCedula(String cedula) throws Exception;
	
	Usuario buscarUsuarioPorCedula(String cedula) throws Exception;
	
	Page<Usuario> filtrarUsuarioPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc) throws Exception;
	
	Usuario buscarUsuarioPorEmail(String email) throws Exception;
		
}
