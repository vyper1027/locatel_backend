package com.ventas.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ventas.app.dto.RolDTO;
import com.ventas.app.entity.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

	Rol rolDTOToRolEntity(RolDTO rolDTO) throws Exception;
	
	RolDTO rolEntityToRolDTO(Rol rol) throws Exception;
	
	List<Rol> rolesDTOToRolesEntity(List<RolDTO> rolesDTO) throws Exception;
	
	List<RolDTO> rolesEntityToRolesDTO(List<Rol> roles) throws Exception;
}
