package com.ventas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.app.dto.JwtDTO;
import com.ventas.app.dto.AuthDTO;
import com.ventas.app.entity.Usuario;
import com.ventas.app.service.UsuarioService;
import com.ventas.app.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Operation(summary = "Login")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = String.class)) }
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
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthDTO authDTO) throws Exception {
		try {
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authDTO.getEmail(),
					authDTO.getContrasenia()
            ));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Usuario usuario = usuarioService.buscarUsuarioPorEmail(authDTO.getEmail());
			
			String jwt = jwtUtil.insertClaimsToToken(usuario);
			
			return new ResponseEntity<>(new JwtDTO(jwt), HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
