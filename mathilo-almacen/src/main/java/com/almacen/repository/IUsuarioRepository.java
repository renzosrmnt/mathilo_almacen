package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
     public Usuario findByEmail(String email);

}






