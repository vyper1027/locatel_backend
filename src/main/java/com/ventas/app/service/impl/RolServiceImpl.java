package com.ventas.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Rol;
import com.ventas.app.jpa.RolJpaRepository;
import com.ventas.app.service.RolService;

@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
	private RolJpaRepository rolJpaRepository;

	@Override
	public Rol crearRol(Rol rol) throws Exception {
		return rolJpaRepository.save(rol);
	}

	@Override
	public void eliminarRolPorId(Long id) throws Exception {
		rolJpaRepository.deleteById(id);
	}

	@Override
	public Rol buscarRolPorId(Long id) throws Exception {
		return rolJpaRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Rol> filtrarRolPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return rolJpaRepository.findAll(PageRequest.of(pagina, elementosPorPagina).withSort(sorting));
	}

	@Override
	public Rol buscarRolPorNombre(String nombre) throws Exception {
		return rolJpaRepository.buscarRolPorNombre(nombre);
	}

}
