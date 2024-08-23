package com.ventas.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ventas.app.dto.UsuarioRequestDTO;
import com.ventas.app.dto.UsuarioResponseDTO;
import com.ventas.app.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	
	
	@Mappings({
		@Mapping(source = "rol", target = "rol.nombre")
	})
	Usuario usuarioRequestDTOToUsuarioEntity(UsuarioRequestDTO usuarioRequestDTO) throws Exception;
	
	@Mappings({
		@Mapping(source = "rol.nombre", target = "rol")
	})
	UsuarioResponseDTO usuarioEntityToUsuarioResponseDTO(Usuario usuario) throws Exception;
	
	List<UsuarioResponseDTO> usuariosEntityToUsuariosResponseDTO(List<Usuario> usuarios) throws Exception;
}
