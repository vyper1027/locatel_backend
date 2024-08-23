package com.ventas.app.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Usuario;
import com.ventas.app.jpa.UsuarioJpaRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioJpaRepository usuarioJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Usuario usuario = usuarioJpaRepository.buscarUsuarioPorEmail(email);
	
			if(usuario == null) {
				throw new UsernameNotFoundException("El email " + email + " No existe");
			}
	
			return new User(usuario.getEmail(), usuario.getContrasenia(), new ArrayList<>());
		}
		catch(EmptyResultDataAccessException exception) {
			throw new UsernameNotFoundException("El usuario no existe...");
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
