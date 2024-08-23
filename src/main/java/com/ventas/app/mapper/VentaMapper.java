package com.ventas.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ventas.app.dto.VentaDTO;
import com.ventas.app.entity.Venta;


@Mapper(componentModel = "spring")
public interface VentaMapper {
	
	@Mappings({
		@Mapping(source = "clienteCedula", target = "cliente.cedula")
	})
	Venta ventaDTOToVentaEntity(VentaDTO ventaDTO) throws Exception;
	
	
	@Mappings({
		@Mapping(source = "cliente.cedula", target = "clienteCedula")
	})
	VentaDTO ventaEntityToVentaDTO(Venta venta) throws Exception;
	
	List<Venta> ventasDTOToVentasEntity(List<VentaDTO> ventasDTO) throws Exception;
	
	List<VentaDTO> ventasEntityToVentasDTO(List<Venta> ventas) throws Exception;

}
