package com.almacen.service.usuarioService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.almacen.dto.UsuarioDTO;
import com.almacen.entitty.Usuario;

import java.util.List;

public interface UserService extends UserDetailsService{

	public Usuario guardar(UsuarioDTO registroDTO);
	
	public List<Usuario> listarUsuarios();

	public Page<Usuario> findAll(Pageable pageable);
	
}



