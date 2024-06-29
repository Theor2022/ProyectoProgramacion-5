package com.cooweb.dao;

import java.util.List;

import com.cooweb.model.Usuario;

import jakarta.transaction.Transactional;

@Transactional
public interface UsuarioDao {

	
	List<Usuario> GetUsuarios();

	void eliminar(Long id);

	Object registrar(Usuario usuario);

	boolean verificarCredenciales(Usuario usuario);



	
	
}
