package com.ventas.app.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.app.dto.APIResponseDTO;
import com.ventas.app.dto.UsuarioRequestDTO;
import com.ventas.app.dto.UsuarioResponseDTO;
import com.ventas.app.entity.Rol;
import com.ventas.app.entity.Usuario;
import com.ventas.app.enums.RolEnum;

import com.ventas.app.mapper.UsuarioMapper;
import com.ventas.app.service.RolService;
import com.ventas.app.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;

	
	@Operation(summary = "Crear usuario")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = UsuarioResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = Exception.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = Exception.class)) }
        ) 
    })
	@PostMapping()
	public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) throws Exception {
		try {
			
			Usuario usuarioEntityDB = usuarioService.buscarUsuarioPorCedula(usuarioRequestDTO.getCedula());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(usuarioEntityDB != null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El usuario ya existe...");
			}
			
			String encryptedPassword = ENCODER.encode(usuarioRequestDTO.getContrasenia());
			
			usuarioRequestDTO.setContrasenia(encryptedPassword);
			
			Usuario newUsuarioEntity = usuarioMapper.usuarioRequestDTOToUsuarioEntity(usuarioRequestDTO);
			
			Rol rolEntity = rolService.buscarRolPorNombre(RolEnum.CLIENTE.toString());			
			newUsuarioEntity.setRol(rolEntity);
			
			newUsuarioEntity = usuarioService.crearUsuario(newUsuarioEntity);

			UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.usuarioEntityToUsuarioResponseDTO(newUsuarioEntity);
			
			return new ResponseEntity<>(usuarioResponseDTO, httpStatus);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Eliminar usuario por cédula")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = Exception.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = Exception.class)) }
        ) 
    })
	@SecurityRequirement(name = "Bearer Authentication")
	@DeleteMapping("/{cedula}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable String cedula) throws Exception {
		try {
			Usuario usuarioEntityDB = usuarioService.buscarUsuarioPorCedula(cedula);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(usuarioEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El usuario no existe...");
			}
			
			usuarioService.eliminarUsuarioPorCedula(cedula);

			return ResponseEntity
					.status(httpStatus)
					.body("Usuario eliminado satisfactoriamente...");
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Actualizar usuario por cédula")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = UsuarioResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = Exception.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = Exception.class)) }
        ) 
    })
	@SecurityRequirement(name = "Bearer Authentication")
	@PutMapping("/{cedula}")
	public ResponseEntity<?> ActualizarUsuarioPorCedula(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO, @PathVariable String cedula) throws Exception {
		try {	
			
			// Cliente existente en base de datos
			Usuario usuarioEntityDB = usuarioService.buscarUsuarioPorCedula(cedula);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(usuarioEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El usuario no existe...");
			}
			
			// Si la contrseña es diferente se actualiza
			if(!usuarioEntityDB.getContrasenia().equals(usuarioRequestDTO.getContrasenia())) {
				String encryptedPassword = ENCODER.encode(usuarioRequestDTO.getContrasenia());
				usuarioRequestDTO.setContrasenia(encryptedPassword);
			}
			
			// Mapear cliente nuevo DTO a entity
			Usuario newClienteEntity = usuarioMapper.usuarioRequestDTOToUsuarioEntity(usuarioRequestDTO);
			
			// Se copia la información del cliente nuevo al cliente en base de datos
			BeanUtils.copyProperties(newClienteEntity, usuarioEntityDB);
		
			newClienteEntity = usuarioService.crearUsuario(usuarioEntityDB);
			
			UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.usuarioEntityToUsuarioResponseDTO(newClienteEntity);

			return new ResponseEntity<>(usuarioResponseDTO, httpStatus);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Buscar usuario por cédula")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = UsuarioResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = Exception.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = Exception.class)) }
        ) 
    })
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/{cedula}")
	public ResponseEntity<?> buscarUsuarioPorCedula(@PathVariable("cedula") String cedula) throws Exception {
		
		try {
			Usuario usuarioEntity = usuarioService.buscarUsuarioPorCedula(cedula);
			
			UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.usuarioEntityToUsuarioResponseDTO(usuarioEntity);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(usuarioResponseDTO == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			return new ResponseEntity<>(usuarioResponseDTO, httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

	@Operation(summary = "Filtrar usuarios por paginación y campo")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = Exception.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = Exception.class)) }
        ) 
    })
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping()
	public ResponseEntity<?> filtrarUsuarioPorPaginacionYCampo(
			@RequestParam(defaultValue = "1") Integer pagina, 
			@RequestParam(defaultValue = "10") Integer elementosPorPagina, 
			@RequestParam(defaultValue = "nombre", required = false) String campo,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			pagina = pagina - 1;
			
			Page<Usuario> clientesEntity = usuarioService.filtrarUsuarioPorPaginacionYCampo(pagina, elementosPorPagina, campo, asc);
			
			List<UsuarioResponseDTO> usuariosResponseDTO = usuarioMapper.usuariosEntityToUsuariosResponseDTO(clientesEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(usuariosResponseDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(clientesEntity.getSize())
					.totalRecordCount(clientesEntity.getTotalElements())
					.totalPages(clientesEntity.getTotalPages())
					.content(usuariosResponseDTO)
					.build(),
					httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
