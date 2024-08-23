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
import com.ventas.app.dto.ProductoDTO;
import com.ventas.app.entity.Producto;
import com.ventas.app.mapper.ProductoMapper;
import com.ventas.app.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ProductoMapper productoMapper;
	
	@Autowired
	private ProductoService productoService;
	

	@Operation(summary = "Crear producto")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = ProductoDTO.class)) }
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
	public ResponseEntity<?> crearProducto(@RequestBody @Valid ProductoDTO productoDTO) throws Exception {
		try {
			
			Producto newProductoEntity = productoMapper.productoDTOToProdcutoEntity(productoDTO);
			
			newProductoEntity = productoService.crearProducto(newProductoEntity);

			productoDTO = productoMapper.productoEntityToProductoDTO(newProductoEntity);
			
			return new ResponseEntity<>(productoDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Eliminar producto por código")
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
	public ResponseEntity<?> eliminarProducto(@PathVariable Long codigo) throws Exception {
		try {
			Producto productoEntityDB = productoService.buscarProductoPorCodigo(codigo);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(productoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El producto no existe...");
			}
			
			productoService.eliminarProductoPorCodigo(codigo);

			return ResponseEntity
					.status(httpStatus)
					.body("Producto eliminado satisfactoriamente...");
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Actualizar producto por código")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = ProductoDTO.class)) }
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
	public ResponseEntity<?> ActualizarProductoPorCodigo(@RequestBody @Valid ProductoDTO productoDTO, @PathVariable Long codigo) throws Exception {
		try {	
			
			// Producto existente en base de datos
			Producto productoEntityDB = productoService.buscarProductoPorCodigo(codigo);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(productoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body("El usuario no existe...");
			}
			
			
			// Mapear producto nuevo DTO a entity
			Producto newProductoEntity = productoMapper.productoDTOToProdcutoEntity(productoDTO);
			
			// Se copia la información del producto nuevo al producto en base de datos
			BeanUtils.copyProperties(newProductoEntity, productoEntityDB);
		
			newProductoEntity = productoService.crearProducto(productoEntityDB);
			
			productoDTO = productoMapper.productoEntityToProductoDTO(newProductoEntity);

			return new ResponseEntity<>(productoDTO, httpStatus);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Buscar producto por código")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = ProductoDTO.class)) }
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
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarProductoPorCodigo(@PathVariable("codigo") Long codigo) throws Exception {
		
		try {
			Producto productoEntity = productoService.buscarProductoPorCodigo(codigo);
			
			ProductoDTO productoDTO = productoMapper.productoEntityToProductoDTO(productoEntity);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(productoDTO == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			return new ResponseEntity<>(productoDTO, httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

	@Operation(summary = "Filtrar productos por paginación y campo")
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
			
			Page<Producto> productosEntity = productoService.filtrarProductoPorPaginacionYCampo(pagina, elementosPorPagina, campo, asc);
			
			List<ProductoDTO> productosDTO = productoMapper.productosEntityToProductosDTO(productosEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(productosDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(productosEntity.getSize())
					.totalRecordCount(productosEntity.getTotalElements())
					.totalPages(productosEntity.getTotalPages())
					.content(productosDTO)
					.build(),
					httpStatus);	
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
