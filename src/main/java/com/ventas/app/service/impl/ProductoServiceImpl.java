package com.ventas.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Producto;
import com.ventas.app.jpa.ProductoJpaRepository;
import com.ventas.app.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoJpaRepository productoJpaRepository;

	@Override
	public Producto crearProducto(Producto producto) throws Exception {
		return productoJpaRepository.save(producto);
	}

	@Override
	public void eliminarProductoPorCodigo(Long codigo) throws Exception {
		productoJpaRepository.deleteById(codigo);
	}

	@Override
	public Producto buscarProductoPorCodigo(Long codigo) throws Exception {
		return productoJpaRepository.findById(codigo).orElse(null);
	}

	@Override
	public Page<Producto> filtrarProductoPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return productoJpaRepository.findAll(PageRequest.of(pagina, elementosPorPagina).withSort(sorting));
	}

}
