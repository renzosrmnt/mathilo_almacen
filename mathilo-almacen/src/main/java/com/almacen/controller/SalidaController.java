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
import com.almacen.entitty.Pedido;
import com.almacen.entitty.Salida;
import com.almacen.service.articuloService.ArticuloService;
import com.almacen.service.pedidoService.PedidoService;
import com.almacen.service.salidaService.SalidaService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

@Controller
public class SalidaController {

	@Autowired
	private SalidaService salidaService;

	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/salida/{id}")
	public String verDetallesSalida(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Salida salida = salidaService.findById(id);
		if (salida == null) {
			flash.addFlashAttribute("error", "La salida no existe en la base de datos");
			return "redirect:/salidas";
		}

		model.addAttribute("salida", salida);
		model.addAttribute("titulo", "Detalles de la salida");

		return "salida/detallesSalida";
	}

	@GetMapping("/salidas")
	public String listarSalidas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Salida> salidas = salidaService.findAll(pageRequest);
		PageRender<Salida> pageRender = new PageRender<>("/salidas", salidas);

		model.addAttribute("titulo", "Listado de salidas");
		model.addAttribute("salidas", salidas);
		model.addAttribute("page", pageRender);

		return "salida/listarSalidas";
	}

	@GetMapping("/nueva-salida")
	public String mostrarFormularioSalida(Model model) {
		model.addAttribute("salida", new Salida());
		model.addAttribute("titulo", "Registro de Salidas");

		List<Articulo> articulos = articuloService.findAll();
		List<Pedido> pedidos = pedidoService.findAll();
		model.addAttribute("articulos", articulos);
		model.addAttribute("pedidos", pedidos);
		return "salida/formularioSalida";
	}

	@PostMapping("/guardar-salida")
	public String guardarSalida(@Valid Salida salida, BindingResult result, Model model, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registro de salida");
			return "salida/formularioSalida";
		}

		try {
			salidaService.save(salida);

			// Reducción del stock del artículo relacionado
			Articulo articulo = salida.getArticulo();
			int cantidad = salida.getCantidad();
			if (articulo != null) {
				int nuevoStock = articulo.getStock() - cantidad;
				articulo.setStock(nuevoStock);
				articuloService.save(articulo);
			}

			flash.addFlashAttribute("success", "¡Salida registrada con éxito!");
			return "redirect:/salidas";
		} catch (Exception e) {
			flash.addFlashAttribute("error", "Error al registrar la salida");
			return "redirect:/nueva-salida";
		}
	}

	@GetMapping("/editar-salida/{id}")
	public String editarSalida(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
	    Salida salida = salidaService.findById(id);
	    
	    if (salida == null) {
	        flash.addFlashAttribute("error", "La salida no existe en la base de datos");
	        return "redirect:/salidas";
	    }

	    model.addAttribute("salida", salida);
	    model.addAttribute("titulo", "Edición de salida");

	    List<Articulo> articulos = articuloService.findAll();
	    model.addAttribute("articulos", articulos);

	    return "salida/formularioSalida";
	}

	@GetMapping("/eliminar-salida/{id}")
	public String eliminarSalida(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Salida salida = salidaService.findById(id);

			if (salida != null) {
				Articulo articulo = salida.getArticulo();
				int cantidad = salida.getCantidad();

				if (articulo != null) {
					int nuevoStock = articulo.getStock() + cantidad;
					articulo.setStock(nuevoStock);
					articuloService.save(articulo);
				}

				salidaService.delete(id);
				flash.addFlashAttribute("success", "Salida eliminada con éxito");
			}
		}
		return "redirect:/salidas";
	}

}
