package com.ventas.app.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ventas.app.dto.VentaDTO;
import com.ventas.app.entity.Venta;
import com.ventas.app.mapper.VentaMapper;
import com.ventas.app.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/venta")
public class VentaController {

	@Autowired
	private VentaMapper ventaMapper;
	
	@Autowired
	private VentaService ventaService;
	
	
	@Operation(summary = "Crear venta")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = VentaDTO.class)) }
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
	@PostMapping()
	public ResponseEntity<?> crearVenta(@RequestBody @Valid VentaDTO ventaDTO) throws Exception {
		try {
			
			Venta newVentaEntity = ventaMapper.ventaDTOToVentaEntity(ventaDTO);

			newVentaEntity = ventaService.crearVenta(newVentaEntity);

			ventaDTO = ventaMapper.ventaEntityToVentaDTO(newVentaEntity);
			
			return new ResponseEntity<>(ventaDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Eliminar venta por código")
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
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long codigo) throws Exception {
		try {
			Venta ventaEntityDB = ventaService.buscarVentaPorCodigo(codigo);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(ventaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("La venta no existe...");
			}
			
			ventaService.eliminarVentaPorCodigo(codigo);

			return ResponseEntity
					.status(httpStatus)
					.body("Venta eliminada satisfactoriamente...");
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Actualizar venta por código")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = VentaDTO.class)) }
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
	@PutMapping("/{codigo}")
	public ResponseEntity<?> ActualizarVentaPorCodigo(@RequestBody @Valid VentaDTO ventaDTO, @PathVariable Long codigo) throws Exception {
		try {	
			
			// Venta existente en base de datos
			Venta ventaEntityDB = ventaService.buscarVentaPorCodigo(codigo);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(ventaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("La venta no existe...");
			}
			
			// Mapear venta nuevo DTO a entity
			Venta newVentaEntity = ventaMapper.ventaDTOToVentaEntity(ventaDTO);
			
			// Se copia la información de la venta nueva a la venta en base de datos
			BeanUtils.copyProperties(newVentaEntity, ventaEntityDB);
		
			newVentaEntity = ventaService.crearVenta(ventaEntityDB);
			
			ventaDTO = ventaMapper.ventaEntityToVentaDTO(newVentaEntity);

			return new ResponseEntity<>(ventaDTO, httpStatus);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Buscar venta por código")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = VentaDTO.class)) }
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
	public ResponseEntity<?> buscarVentaPorCodigo(@PathVariable("codigo") Long codigo) throws Exception {
		
		try {
			Venta ventaEntity = ventaService.buscarVentaPorCodigo(codigo);
			
			VentaDTO ventaDTO = ventaMapper.ventaEntityToVentaDTO(ventaEntity);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(ventaDTO == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			return new ResponseEntity<>(ventaDTO, httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

	@Operation(summary = "Filtrar ventas por paginación y campo")
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
	public ResponseEntity<?> filtrarProductoPorPaginacionYCampo(
			@RequestParam(defaultValue = "1") Integer pagina, 
			@RequestParam(defaultValue = "10") Integer elementosPorPagina, 
			@RequestParam(defaultValue = "nombre", required = false) String campo,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			pagina = pagina - 1;
			
			Page<Venta> ventasEntity = ventaService.filtrarVentaPorPaginacionYCampo(pagina, elementosPorPagina, campo, asc);
			
			List<VentaDTO> ventasDTO = ventaMapper.ventasEntityToVentasDTO(ventasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(ventasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(ventasEntity.getSize())
					.totalRecordCount(ventasEntity.getTotalElements())
					.totalPages(ventasEntity.getTotalPages())
					.content(ventasDTO)
					.build(),
					httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
