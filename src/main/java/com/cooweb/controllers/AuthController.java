package com.cooweb.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cooweb.dao.UsuarioDao;
import com.cooweb.model.Usuario;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RestController
public class AuthController {

	@Autowired
	private UsuarioDao usuarioDao;

	// Clave secreta segura para firmar el token (debe mantenerse privada)
	private static final String SECRET_KEY = "TuClaveSecretaDeAlMenos32CaracteresDeLongitudSegura";

	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {
		if (usuarioDao.verificarCredenciales(usuario)) {
			// Firmar el token con la clave secreta
			String token = Jwts.builder()
					.setSubject(usuario.getEmail())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 1 día
					.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY)), SignatureAlgorithm.HS256)
					.compact();

			return ResponseEntity.ok(Collections.singletonMap("token", token));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
	}
}
