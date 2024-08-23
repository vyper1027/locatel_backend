package com.ventas.app.service;

import org.springframework.data.domain.Page;

import com.ventas.app.entity.Venta;


public interface VentaService {
	
	Venta crearVenta(Venta venta) throws Exception;
	
	void eliminarVentaPorCodigo(Long codigo) throws Exception;
	
	Venta buscarVentaPorCodigo(Long codigo) throws Exception;
	
	Page<Venta> filtrarVentaPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc) throws Exception;

}
