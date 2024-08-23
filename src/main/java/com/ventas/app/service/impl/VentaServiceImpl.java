package com.ventas.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Venta;
import com.ventas.app.jpa.VentaJpaRepository;
import com.ventas.app.service.VentaService;

@Service
public class VentaServiceImpl implements VentaService {
	
	@Autowired
	private VentaJpaRepository ventaJpaRepository;

	@Override
	public Venta crearVenta(Venta venta) throws Exception {
		return ventaJpaRepository.save(venta);
	}

	@Override
	public void eliminarVentaPorCodigo(Long codigo) throws Exception {
		ventaJpaRepository.deleteById(codigo);
	}

	@Override
	public Venta buscarVentaPorCodigo(Long codigo) throws Exception {
		return ventaJpaRepository.findById(codigo).orElse(null);
	}

	@Override
	public Page<Venta> filtrarVentaPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return ventaJpaRepository.findAll(PageRequest.of(pagina, elementosPorPagina).withSort(sorting));
	}

}
