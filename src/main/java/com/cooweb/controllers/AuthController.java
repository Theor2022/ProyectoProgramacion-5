package com.cooweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooweb.dao.UsuarioDao;
import com.cooweb.model.Usuario;

@RestController
public class AuthController {

	@Autowired
		private UsuarioDao usuarioDao;
	
	@PostMapping ("/api/login")
	public String login (@RequestBody Usuario usuario) {
	
		if (usuarioDao.verificarCredenciales(usuario)) {
			
			return "OK";
		}
		 	return "FAIL";
	}
	
}
