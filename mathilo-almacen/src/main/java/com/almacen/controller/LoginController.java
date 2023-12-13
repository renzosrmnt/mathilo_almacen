package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.almacen.dto.UsuarioDTO;
import com.almacen.service.usuarioService.UserService;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private UserService userService;
    
	@ModelAttribute("usuario")
	public UsuarioDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioDTO();
	}
    
    @GetMapping("/login")
	public String iniciarSesion(Model model) {
    	model.addAttribute("titulo", "Inicia Sesión");
		return "login";
	}
    
    @GetMapping("/registro")
	public String mostrarFormularioDeRegistro(Model model) {
    	model.addAttribute("titulo", "Registro");
		return "registro";
	}
    
    @PostMapping("/registro")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {
		userService.guardar(usuarioDTO);
		return "redirect:/registro?exito";
	}

    
    
    @GetMapping("/")
	public String verPaginaDeInicio() {
		return "index";
	}
}




