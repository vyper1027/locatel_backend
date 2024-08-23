package com.ventas.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Usuario;
import com.ventas.app.jpa.UsuarioJpaRepository;
import com.ventas.app.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioJpaRepository usuarioJpaRepository;

	@Override
	public Usuario crearUsuario(Usuario cliente) throws Exception {
		return usuarioJpaRepository.save(cliente);
	}

	@Override
	public void eliminarUsuarioPorCedula(String cedula) throws Exception {
		usuarioJpaRepository.deleteById(cedula);
	}

	@Override
	public Usuario buscarUsuarioPorCedula(String cedula) throws Exception {		
		return usuarioJpaRepository.findById(cedula).orElse(null);
	}

	@Override
	public Page<Usuario> filtrarUsuarioPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc)
			throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return usuarioJpaRepository.findAll(PageRequest.of(pagina, elementosPorPagina).withSort(sorting));
	}

	@Override
	public Usuario buscarUsuarioPorEmail(String email) throws Exception {
		return usuarioJpaRepository.buscarUsuarioPorEmail(email);
	}

}
