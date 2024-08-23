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
import com.ventas.app.dto.DetalleVentaDTO;
import com.ventas.app.entity.DetalleVenta;
import com.ventas.app.entity.embeddable.DetalleVentaKey;
import com.ventas.app.mapper.DetalleVentaMapper;
import com.ventas.app.service.DetalleVentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {
	
	@Autowired
	private DetalleVentaMapper detalleVentaMapper;
	
	@Autowired
	private DetalleVentaService detalleVentaService;
	
	
	@Operation(summary = "Crear detalle venta")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DetalleVentaDTO.class)) }
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
	public ResponseEntity<?> crearDetalleVenta(@RequestBody @Valid DetalleVentaDTO detalleVentaDTO) throws Exception {
		try {
			
			DetalleVenta newDetalleVentaEntity = detalleVentaMapper.detalleVentaDTOToDetalleVentaEntity(detalleVentaDTO);
	
			newDetalleVentaEntity = detalleVentaService.crearDetalleVenta(newDetalleVentaEntity);

			detalleVentaDTO = detalleVentaMapper.detalleVentaEntityToDetalleVentaDTO(newDetalleVentaEntity);
			
			return new ResponseEntity<>(detalleVentaDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Eliminar detalle venta por código de producto y código de venta")
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
	@DeleteMapping()
	public ResponseEntity<?> eliminarDetalleVenta(DetalleVentaKey detalleVentaKey) throws Exception {
		try {
			
			DetalleVenta detalleVentaEntityDB = detalleVentaService.buscarDetalleVentaPorCodigo(detalleVentaKey);
				
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(detalleVentaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El usuario no existe...");
			}
			
			detalleVentaService.eliminarDetalleVentaPorCodigo(detalleVentaKey);

			return ResponseEntity
					.status(httpStatus)
					.body("detalle venta eliminada satisfactoriamente...");
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Actualizar detalle venta por código de producto y código de venta")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DetalleVentaDTO.class)) }
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
	@PutMapping("/{producto-codigo}/{venta-codigo}")
	public ResponseEntity<?> ActualizarDetalleVentaPorCodigo(
			@RequestBody @Valid DetalleVentaDTO detalleVentaDTO, 
			@PathVariable("producto-codigo") Long productoCodigo, 
			@PathVariable("venta-codigo") Long ventaCodigo) throws Exception {
		try {	
			
			DetalleVentaKey detalleVentaKey = new DetalleVentaKey();
			
			detalleVentaKey.setProductoCodigo(productoCodigo);
			detalleVentaKey.setVentaCodigo(ventaCodigo);
			
			// detall venta existente en base de datos
			DetalleVenta detalleVentaEntityDB = detalleVentaService.buscarDetalleVentaPorCodigo(detalleVentaKey);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(detalleVentaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El detalle venta no existe...");
			}
			
			// Mapear DetalleVenta nuevo DTO a entity
			DetalleVenta newDetalleVentaEntity = detalleVentaMapper.detalleVentaDTOToDetalleVentaEntity(detalleVentaDTO);
			
			// Se copia la información de DetalleVenta nuevo al DetalleVenta en base de datos
			BeanUtils.copyProperties(newDetalleVentaEntity, detalleVentaEntityDB);
		
			newDetalleVentaEntity = detalleVentaService.crearDetalleVenta(detalleVentaEntityDB);
			
			detalleVentaDTO = detalleVentaMapper.detalleVentaEntityToDetalleVentaDTO(newDetalleVentaEntity);

			return new ResponseEntity<>(detalleVentaDTO, httpStatus);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Buscar detalle venta por código")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DetalleVentaDTO.class)) }
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
	@GetMapping("/{producto-codigo}/{venta-codigo}")
	public ResponseEntity<?> buscarDetalleVentaPorCodigo(
			@PathVariable("producto-codigo") Long productoCodigo, 
			@PathVariable("venta-codigo") Long ventaCodigo) throws Exception {
		
		try {
			
			DetalleVentaKey detalleVentaKey = new DetalleVentaKey();
			detalleVentaKey.setProductoCodigo(productoCodigo);
			detalleVentaKey.setVentaCodigo(ventaCodigo);
			
			DetalleVenta detalleVentaEntity = detalleVentaService.buscarDetalleVentaPorCodigo(detalleVentaKey);
			
			DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.detalleVentaEntityToDetalleVentaDTO(detalleVentaEntity);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(detalleVentaDTO == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			return new ResponseEntity<>(detalleVentaDTO, httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

	@Operation(summary = "Filtrar detalles ventas por paginación y campo")
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
	public ResponseEntity<?> filtrarDetalleVentaPorPaginacionYCampo(
			@RequestParam(defaultValue = "1") Integer pagina, 
			@RequestParam(defaultValue = "10") Integer elementosPorPagina, 
			@RequestParam(defaultValue = "nombre", required = false) String campo,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			pagina = pagina - 1;
			
			Page<DetalleVenta> detallesVentasEntity = detalleVentaService.filtrarDetalleVentaPorPaginacionYCampo(pagina, elementosPorPagina, campo, asc);
			
			List<DetalleVentaDTO> detalleVentaDTO = detalleVentaMapper.detallesVentasEntityToDetallesVentasDTO(detallesVentasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(detalleVentaDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(detallesVentasEntity.getSize())
					.totalRecordCount(detallesVentasEntity.getTotalElements())
					.totalPages(detallesVentasEntity.getTotalPages())
					.content(detalleVentaDTO)
					.build(),
					httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

}
