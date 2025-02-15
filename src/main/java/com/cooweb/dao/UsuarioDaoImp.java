package com.cooweb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cooweb.model.Usuario;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {
	
	@PersistenceContext

	private EntityManager entityManager; 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> GetUsuarios() {
		
		String query="from Usuario";
		 
		
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void eliminar(Long id) {
		
		Usuario usuario=entityManager.find(Usuario.class, id);
		entityManager.remove(usuario);
		
		
	}
	
	@Override
	public Object registrar(Usuario usuario) {
		
		return entityManager.merge(usuario);
		
		
	}

	@Override
	public boolean verificarCredenciales(Usuario usuario) {
		String query = "FROM Usuario u WHERE u.email = :email";

		List<Usuario> lista = entityManager.createQuery(query, Usuario.class)
				.setParameter("email", usuario.getEmail())
				.getResultList();

		if (lista.isEmpty()) {
			return false; // Usuario no encontrado
		}

		Usuario usuarioDB = lista.get(0); // Obtiene el usuario encontrado
		String passHasheada = usuarioDB.getPassword();

		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);

		// Mensaje en consola para depuración sin mostrar la contraseña real
		System.out.println("Contraseña almacenada (HASH): " + passHasheada);
		System.out.println("Verificando credenciales para el usuario: " + usuario.getEmail());

		// Compara la contraseña ingresada con el hash en la BD
		boolean esValida = argon2.verify(passHasheada, usuario.getPassword());

		if (esValida) {
			System.out.println("✅ Contraseña válida");
		} else {
			System.out.println("❌ Contraseña incorrecta");
		}

		return esValida;
	}


}
