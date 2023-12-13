package com.almacen.controller;

import java.util.List;

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

import com.almacen.entitty.Cliente;
import com.almacen.entitty.Pedido;
import com.almacen.service.clienteService.ClienteService;
import com.almacen.service.pedidoService.PedidoService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

@Controller
public class PedidoController {

	@Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;
    

    @GetMapping("/pedido/{id}")
    public String verDetallesPedido(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            flash.addFlashAttribute("error", "El pedido no existe en la base de datos");
            return "redirect:/pedidos";
        }
        
        List<Cliente> clientes= clienteService.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedido", pedido);
        model.addAttribute("titulo", "Detalles del pedido" );
        
        return "pedido/detallesPedido";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Pedido> pedidos = pedidoService.findAll(pageRequest);
        PageRender<Pedido> pageRender = new PageRender<>("/pedidos", pedidos);

        model.addAttribute("titulo", "Listado de pedidos");
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("page", pageRender);

        return "pedido/listarPedidos";
    }

    @GetMapping("/nuevo-pedido")
    public String mostrarFormularioPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("titulo", "Registro de Pedidos");
        
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "pedido/formularioPedido";
    }

    @PostMapping("/guardar-pedido")
    public String guardarPedido(@Valid Pedido pedido, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registro de pedido");
            return "pedido/formularioPedido";
        }
   
        try {
	        pedidoService.save(pedido);
	        status.setComplete();
	        flash.addFlashAttribute("success", "¡Pedido registrada con éxito!");
	        return "redirect:/pedidos";
	    } catch (DataIntegrityViolationException e) {
	        flash.addFlashAttribute("error", "Ha ocurrido un error inesperado");
	        return "redirect:/nuevo-pedido";
	        }
    }

    @GetMapping("/editar-pedido/{id}")
    public String editarPedido(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        if (id != null && id > 0) {
            Pedido pedido= pedidoService.findById(id);
            if (pedido == null) {
                flash.addFlashAttribute("error", "El pedido no existe en la base de datos");
                return "redirect:/pedidos";
            }
            model.addAttribute("pedido", pedido);
        } else {
            flash.addFlashAttribute("error", "El ID del pedido no puede ser nulo o cero");
            return "redirect:/pedidos";
        }
        
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("titulo", "Edición de pedido");
        return "pedido/formularioPedido";
    }

    @GetMapping("/eliminar-pedido/{id}")
    public String eliminarPedido(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            pedidoService.delete(id);
            flash.addFlashAttribute("success", "Pedido eliminado con éxito");
        }
        return "redirect:/pedidos";
    }
	
}
