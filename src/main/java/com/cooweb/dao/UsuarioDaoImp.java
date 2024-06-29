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
	    // Buscar al usuario solo por email
	    String query = "FROM Usuario u WHERE u.email = :email";
	    
	    List<Usuario> lista = entityManager.createQuery(query)
	            .setParameter("email", usuario.getEmail())
	            .getResultList();
	    
	    // Si el usuario no existe, retornar falso
	    if (lista.isEmpty()) {
	        return false;
	    }
	    
	    // Recuperar el hash de la contraseña almacenada
	    String passHasheada = lista.get(0).getPassword();
	    
	    // Crear la instancia de Argon2
	    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
	    
	    // Verificar la contraseña ingresada con el hash almacenado
	    boolean passEsIgual = argon2.verify(passHasheada, usuario.getPassword());
	    
	    // Retornar true si la contraseña es correcta, false si es incorrecta
	    return passEsIgual;
	}

}
