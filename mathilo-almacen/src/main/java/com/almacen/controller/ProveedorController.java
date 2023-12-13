package com.almacen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.almacen.entitty.Proveedor;
import com.almacen.service.proveedorService.ProveedorService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

@Controller
public class ProveedorController {
	@Autowired
	private ProveedorService proveedorService;

	@GetMapping("/proveedor/{id}")
	public String verDetallesProveedor(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
	    Proveedor proveedor = proveedorService.findById(id);
	    if (proveedor == null) {
	        flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
	        return "redirect:/proveedores";
	    }

	    modelo.put("proveedor", proveedor);
	    modelo.put("titulo", "Detalles del proveedor " + proveedor.getNombre());
	    return "proveedor/detallesProveedor";
	}

	@GetMapping("/proveedores")
	public String listarProveedores(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
	    Pageable pageRequest = PageRequest.of(page, 5);
	    Page<Proveedor> proveedores = proveedorService.findAll(pageRequest);
	    PageRender<Proveedor> pageRender = new PageRender<>("/proveedores", proveedores);

	    modelo.addAttribute("titulo", "Listado de proveedores");
	    modelo.addAttribute("proveedores", proveedores);
	    modelo.addAttribute("page", pageRender);

	    return "proveedor/listarProveedores";
	}

	@GetMapping("/nuevo-proveedor")
	public String mostrarFormularioProveedor(Model modelo) {
	    modelo.addAttribute("proveedor", new Proveedor());
	    modelo.addAttribute("titulo", "Registro de proveedor");
	    return "proveedor/formularioProveedor";
	}

	@PostMapping("/guardar-proveedor")
	public String guardarProveedor(@Valid Proveedor proveedor, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
	    if (result.hasErrors()) {
	        modelo.addAttribute("titulo", "Registro de proveedor");
	        return "proveedor/formularioProveedor";
	    }

	    try {
	        proveedorService.save(proveedor);
	        status.setComplete();
	        flash.addFlashAttribute("success", "¡Proveedor registrado con éxito!");
	        return "redirect:/proveedores";
	    } catch (DataIntegrityViolationException e) {
	        flash.addFlashAttribute("error", "El nombre del proveedor ya existe");
	        return "redirect:/nuevo-proveedor";
	    }
	}

	@GetMapping("/editar-proveedor/{id}")
	public String editarProveedor(@PathVariable(value = "id") Long id, Model modelo, RedirectAttributes flash) {
	    if (id != null && id > 0) {
	        Proveedor proveedor = proveedorService.findById(id);
	        if (proveedor == null) {
	            flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
	            return "redirect:/proveedores";
	        }
	        modelo.addAttribute("proveedor", proveedor);
	    } else {
	        flash.addFlashAttribute("error", "El ID del proveedor no puede ser nulo o cero");
	        return "redirect:/proveedores";
	    }

	    modelo.addAttribute("titulo", "Edición de proveedor");
	    return "proveedor/formularioProveedor";
	}

	@GetMapping("/eliminar-proveedor/{id}")
	public String eliminarProveedor(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
	    if (id > 0) {
	        proveedorService.delete(id);
	        flash.addFlashAttribute("success", "Proveedor eliminado con éxito");
	    }
	    return "redirect:/proveedores";
	}


}


