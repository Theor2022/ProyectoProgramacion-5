package com.cooweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooweb.dao.UsuarioDao;
import com.cooweb.model.Usuario;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioDao.GetUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content si no hay usuarios
        }
        return ResponseEntity.ok(usuarios);  // Devuelve 200 OK con la lista de usuarios
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioDao.eliminar(id);
        return ResponseEntity.ok().build();  // Devuelve 200 OK después de eliminar
    }

    @PostMapping
    public Object registrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña no puede estar vacía");
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hash = argon2.hash(2, 1024, 2, usuario.getPassword()); // Aumentar iteraciones para más seguridad
        usuario.setPassword(hash);

        return usuarioDao.registrar(usuario);
    }
        
    	
}

