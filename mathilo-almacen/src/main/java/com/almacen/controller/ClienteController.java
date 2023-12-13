package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.almacen.entitty.Cliente;
import com.almacen.service.clienteService.ClienteService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

import java.util.*;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/cliente/{id}")
	public String verDetallesCliente(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
	    Cliente cliente = clienteService.findById(id);
	    if (cliente == null) {
	        flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
	        return "redirect:/clientes";
	    }

	    modelo.put("cliente", cliente);
	    modelo.put("titulo", "Detalles del cliente " + cliente.getNombre() + " " + cliente.getApellido());
	    return "cliente/detallesCliente";
	}

	@GetMapping("/clientes")
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
	    Pageable pageRequest = PageRequest.of(page, 5);
	    Page<Cliente> clientes = clienteService.findAll(pageRequest);
	    PageRender<Cliente> pageRender = new PageRender<>("/clientes", clientes);

	    modelo.addAttribute("titulo", "Listado de clientes");
	    modelo.addAttribute("clientes", clientes);
	    modelo.addAttribute("page", pageRender);

	    return "cliente/listarClientes";
	}

	@GetMapping("/nuevo-cliente")
	public String mostrarFormularioCliente(Model modelo) {
	    modelo.addAttribute("cliente", new Cliente());
	    modelo.addAttribute("titulo", "Registro de cliente");
	    return "cliente/formularioCliente";
	}

	@PostMapping("/guardar-cliente")
	public String guardarCliente(@Valid Cliente cliente, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
	    if (result.hasErrors()) {
	        modelo.addAttribute("titulo", "Registro de cliente");
	        return "cliente/formularioCliente";
	    }

	    try {
	        clienteService.save(cliente);
	        status.setComplete();
	        flash.addFlashAttribute("success", "¡Cliente registrado con éxito!");
	        return "redirect:/clientes";
	    } catch (DataIntegrityViolationException e) {
	        flash.addFlashAttribute("error", "El nombre del cliente ya existe");
	        return "redirect:/nuevo-cliente";
	    }
	}

	@GetMapping("/editar-cliente/{id}")
	public String editarCliente(@PathVariable(value = "id") Long id, Model modelo, RedirectAttributes flash) {
	    if (id != null && id > 0) {
	        Cliente cliente = clienteService.findById(id);
	        if (cliente == null) {
	            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
	            return "redirect:/clientes";
	        }
	        modelo.addAttribute("cliente", cliente);
	    } else {
	        flash.addFlashAttribute("error", "El ID del cliente no puede ser nulo o cero");
	        return "redirect:/clientes";
	    }

	    modelo.addAttribute("titulo", "Edición de cliente");
	    return "cliente/formularioCliente";
	}

	@GetMapping("/eliminar-cliente/{id}")
	public String eliminarCliente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
	    if (id > 0) {
	        clienteService.delete(id);
	        flash.addFlashAttribute("success", "Cliente eliminado con éxito");
	    }
	    return "redirect:/clientes";
	}



}
