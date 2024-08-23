package com.ventas.app.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ventas.app.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    // Obtener username del token.
    public String getUserNameFromJwtToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    // Obtener Fecha de expiración del token.
    public Date getExpirationDateFromJwtToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    // Obtener los parámetros del token a partir de un atributo.
    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    // Crea una nueva instancia de SecretKey.
    private Key getSignKey() { 
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 

    // Obtener todos los parámetros del token.
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
        		.parser()
        		.verifyWith((SecretKey) getSignKey())
        		.build()
        		.parseSignedClaims(token)
        		.getPayload();
    }

    // Verifica si el token ha expirado.
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromJwtToken(token);
        return expiration.before(new Date());
    }

    // Inserta en el token los atributos o claims.
    public String insertClaimsToToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getCedula());
        claims.put("nombre", usuario.getNombre());
        claims.put("rol", usuario.getRol().getNombre());
        return generateToken(claims, usuario.getEmail());
    }

    // Generar token para usuario.
    private String generateToken(Map<String, Object> claims, String subject) {
    	return Jwts
    			.builder()
    			.claims(claims)
    			.subject(subject)
    			.issuedAt(new Date(System.currentTimeMillis()))
    			.expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
    			.signWith(getSignKey())
    			.compact();  
    }

    // Validar si el token es valido a partir del username y que no este expirado.
    public boolean validateToken(String token, UserDetails userDetails) {
    	 try {
    		 
	    	Jwts
	    	.parser()
	    	.verifyWith((SecretKey) getSignKey())
	    	.build().parseSignedClaims(token);
	    	
	        final String username = getUserNameFromJwtToken(token);
	        
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    	 } 
    	 catch (MalformedJwtException e) {
    		 System.out.println("Invalid JWT token: {} " + e.getMessage());
         } 
    	 catch (ExpiredJwtException e) {
    		 System.out.println("JWT token is expired: {} " + e.getMessage());
         } 
    	 catch (UnsupportedJwtException e) {
             System.out.println("JWT token is unsupported: {} " + e.getMessage());
         } 
    	 catch (IllegalArgumentException e) {
    		 System.out.println("JWT claims string is empty: {} " + e.getMessage());
         }
    	 
         return false;
    }
}
