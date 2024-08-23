package com.ventas.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ventas.app.dto.DetalleVentaDTO;
import com.ventas.app.entity.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

	@Mappings({
		@Mapping(source = "codigo.productoCodigo", target = "producto.codigo"),
		@Mapping(source = "codigo.ventaCodigo", target = "venta.codigo")	
	})
	DetalleVenta detalleVentaDTOToDetalleVentaEntity(DetalleVentaDTO detalleVentaDTO) throws Exception;
	
	
	@Mappings({
		@Mapping(source = "producto.codigo", target = "codigo.productoCodigo"),
		@Mapping(source = "venta.codigo", target = "codigo.ventaCodigo")	
	})
	DetalleVentaDTO detalleVentaEntityToDetalleVentaDTO(DetalleVenta detalleVenta) throws Exception;
	
	List<DetalleVenta> detallesVentasDTOToDetallesVentasEntity(List<DetalleVentaDTO> detallesVentasDTO) throws Exception;
	
	List<DetalleVentaDTO> detallesVentasEntityToDetallesVentasDTO(List<DetalleVenta> detallesVentas) throws Exception;
}
