package com.ventas.app.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.DetalleVenta;
import com.ventas.app.entity.embeddable.DetalleVentaKey;
import com.ventas.app.jpa.DetalleVentaJpaRepository;
import com.ventas.app.service.DetalleVentaService;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {
	
	@Autowired
	private DetalleVentaJpaRepository detalleVentaJpaRepository;

	@Override
	public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) throws Exception {
		return detalleVentaJpaRepository.save(detalleVenta);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<DetalleVenta> crearDetallesVentas(Set<DetalleVenta> detallesVentas) throws Exception {
		return (Set<DetalleVenta>) detalleVentaJpaRepository.saveAll(detallesVentas);
	}

	@Override
	public void eliminarDetalleVentaPorCodigo(DetalleVentaKey codigo) throws Exception {
		detalleVentaJpaRepository.deleteById(codigo);
	}

	@Override
	public DetalleVenta buscarDetalleVentaPorCodigo(DetalleVentaKey codigo) throws Exception {
		return detalleVentaJpaRepository.findById(codigo).orElse(null);
	}

	@Override
	public Page<DetalleVenta> filtrarDetalleVentaPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina,
			String campo, boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return detalleVentaJpaRepository.findAll(PageRequest.of(pagina, elementosPorPagina).withSort(sorting));
	}

}
