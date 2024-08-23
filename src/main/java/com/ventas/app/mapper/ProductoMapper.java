package com.ventas.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ventas.app.dto.ProductoDTO;
import com.ventas.app.entity.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

	Producto productoDTOToProdcutoEntity(ProductoDTO productoDTO) throws Exception;
	
	ProductoDTO productoEntityToProductoDTO(Producto producto) throws Exception;
	
	List<Producto> productosDTOToProductosEntity(List<ProductoDTO> productosDTO) throws Exception;
	
	List<ProductoDTO> productosEntityToProductosDTO(List<Producto> productos) throws Exception;
	
}
