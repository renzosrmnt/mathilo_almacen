package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.almacen.entitty.Usuario;
import com.almacen.service.usuarioService.UserService;
import com.almacen.util.pagination.PageRender;

@Controller
public class UsuarioController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/usuarios")
	public String listarUsuarios(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Usuario> usuarios = userService.findAll(pageRequest);
		PageRender<Usuario> pageRender = new PageRender<>("/usuarios", usuarios);

		modelo.addAttribute("titulo","Listado de Usuarios");
		modelo.addAttribute("usuarios",usuarios);
		modelo.addAttribute("page", pageRender);

		return "usuario/listarUsuarios";
	}
	
	@GetMapping("/nuevo-usuario")
	public String mostrarFormularioUsuario(Model modelo) {
	    modelo.addAttribute("usuario", new Usuario());
	    modelo.addAttribute("titulo", "Registro de cliente");
	    return "usuario/guardarUsuario";
	}


}
