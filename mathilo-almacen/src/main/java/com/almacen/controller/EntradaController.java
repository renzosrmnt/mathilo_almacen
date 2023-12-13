package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.almacen.entitty.Articulo;
import com.almacen.entitty.Entrada;
import com.almacen.entitty.Proveedor;
import com.almacen.service.articuloService.ArticuloService;
import com.almacen.service.entradaService.EntradaService;
import com.almacen.service.proveedorService.ProveedorService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

@Controller
public class EntradaController {

	@Autowired
	private EntradaService entradaService;

	@Autowired
	private ArticuloService articuloService;

	@Autowired
	private ProveedorService proveedorService;

	@GetMapping("/entrada/{id}")
	public String verDetallesEntrada(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Entrada entrada = entradaService.findById(id);
		if (entrada == null) {
			flash.addFlashAttribute("error", "La entrada no existe en la base de datos");
			return "redirect:/entradas";
		}

		model.addAttribute("entrada", entrada);
		model.addAttribute("titulo", "Detalles de la entrada");

		return "entrada/detallesEntrada";
	}

	@GetMapping("/entradas")
	public String listarEntradas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Entrada> entradas = entradaService.findAll(pageRequest);
		PageRender<Entrada> pageRender = new PageRender<>("/entradas", entradas);

		model.addAttribute("titulo", "Listado de entradas");
		model.addAttribute("entradas", entradas);
		model.addAttribute("page", pageRender);

		return "entrada/listarEntradas";
	}

	@GetMapping("/nueva-entrada")
	public String mostrarFormularioEntrada(Model model) {
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("titulo", "Registro de Entradas");

		List<Articulo> articulos = articuloService.findAll();
		List<Proveedor> proveedores = proveedorService.findAll();

		model.addAttribute("articulos", articulos);
		model.addAttribute("proveedores", proveedores);

		return "entrada/formularioEntrada";
	}

	@PostMapping("/guardar-entrada")
	public String guardarEntrada(@Valid Entrada entrada, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registro de entrada");
			return "entrada/formularioEntrada";
		}

		try {
			entradaService.save(entrada);

			// Aumentar el stock del artículo relacionado
			Articulo articulo = entrada.getArticulo();
			int cantidad = entrada.getCantidad();
			if (articulo != null) {
				int nuevoStock = articulo.getStock() + cantidad;
				articulo.setStock(nuevoStock);
				articuloService.save(articulo);
			}

			flash.addFlashAttribute("success", "¡Entrada registrada con éxito!");
			return "redirect:/entradas";
		} catch (Exception e) {
			flash.addFlashAttribute("error", "Error al registrar la entrada");
			return "redirect:/nueva-entrada";
		}
	}

	@GetMapping("/editar-entrada/{id}")
	public String editarEntrada(@PathVariable(value = "id") Long id, Model modelo, RedirectAttributes flash) {
		if (id != null && id > 0) {
			Entrada entrada = entradaService.findById(id);
			if (entrada == null) {
				flash.addFlashAttribute("error", "La entrada no existe en la base de datos");
				return "redirect:/entradas";
			}
			modelo.addAttribute("entrada", entrada);

			List<Articulo> articulos = articuloService.findAll();
			modelo.addAttribute("articulos", articulos);

			List<Proveedor> proveedores = proveedorService.findAll();
			modelo.addAttribute("proveedores", proveedores);
		} else {
			flash.addFlashAttribute("error", "El ID de la entrada no puede ser nulo o cero");
			return "redirect:/entradas";
		}

		modelo.addAttribute("titulo", "Edición de entrada");
		return "entrada/formularioEntrada";
	}

	@GetMapping("/eliminar-entrada/{id}")
	public String eliminarEntrada(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Entrada entrada = entradaService.findById(id);

			if (entrada != null) {
				Articulo articulo = entrada.getArticulo();
				int cantidad = entrada.getCantidad();

				if (articulo != null) {
					int nuevoStock = articulo.getStock() - cantidad;
					articulo.setStock(nuevoStock);
					articuloService.save(articulo);
				}

				entradaService.delete(id);
				flash.addFlashAttribute("success", "Entrada eliminada con éxito");
			}
		}
		return "redirect:/entradas";
	}
}
