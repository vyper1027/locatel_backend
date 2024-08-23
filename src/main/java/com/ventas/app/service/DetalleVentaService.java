package com.ventas.app.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.ventas.app.entity.DetalleVenta;
import com.ventas.app.entity.embeddable.DetalleVentaKey;

public interface DetalleVentaService {

	DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) throws Exception;
	
	Set<DetalleVenta> crearDetallesVentas(Set<DetalleVenta> detallesVentas) throws Exception;
	
	void eliminarDetalleVentaPorCodigo(DetalleVentaKey codigo) throws Exception;
	
	DetalleVenta buscarDetalleVentaPorCodigo(DetalleVentaKey codigo) throws Exception;
	
	Page<DetalleVenta> filtrarDetalleVentaPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc) throws Exception;
	
}
