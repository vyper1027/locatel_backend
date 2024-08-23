package com.ventas.app.service;


import org.springframework.data.domain.Page;

import com.ventas.app.entity.Producto;

public interface ProductoService {

	Producto crearProducto(Producto producto) throws Exception;
	
	void eliminarProductoPorCodigo(Long codigo) throws Exception;
	
	Producto buscarProductoPorCodigo(Long codigo) throws Exception;
	
	Page<Producto> filtrarProductoPorPaginacionYCampo(Integer pagina, Integer elementosPorPagina, String campo, boolean asc) throws Exception;
	
}
