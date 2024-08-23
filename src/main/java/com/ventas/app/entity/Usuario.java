package com.ventas.app.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario implements Serializable, UserDetails{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	@Id
	@Column(unique = true)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String cedula;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "direccion", nullable = false)
	private String direccion;
	
	@Column(name = "telefono", nullable = false)
	private String telefono;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "contraseña", nullable = false)
	private String contrasenia;
	
	//@OneToOne
	//@JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rol_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
    private Rol rol;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROL_" + rol.getNombre().toString());
        return List.of(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
	
}
