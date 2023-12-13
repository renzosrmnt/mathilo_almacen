package com.almacen.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.almacen.entitty.Articulo;
import com.almacen.entitty.Categoria;
import com.almacen.service.articuloService.ArticuloService;
import com.almacen.service.categoriaService.CategoriaService;
import com.almacen.util.pagination.PageRender;

import jakarta.validation.Valid;

@Controller
public class ArticuloController {

	@Autowired
    private ArticuloService articuloService;
    @Autowired
    private CategoriaService categoriaService;
    

    @GetMapping("/articulo/{id}")
    public String verDetallesArticulo(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Articulo articulo = articuloService.findById(id);
        if (articulo == null) {
            flash.addFlashAttribute("error", "El articulo no existe en la base de datos");
            return "redirect:/articulos";
        }
        
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("articulo", articulo);
        model.addAttribute("titulo", "Detalles del articulo " + articulo.getNombre());
        
        return "articulo/detallesArticulo";
    }

    @GetMapping("/articulos")
    public String listarArticulos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Articulo> articulos = articuloService.findAll(pageRequest);
        PageRender<Articulo> pageRender = new PageRender<>("/articulos", articulos);

        model.addAttribute("titulo", "Listado de articulos");
        model.addAttribute("articulos", articulos);
        model.addAttribute("page", pageRender);

        return "articulo/listarArticulos";
    }

    @GetMapping("/nuevo-articulo")
    public String mostrarFormularioArticulo(Model model) {
        model.addAttribute("articulo", new Articulo());
        model.addAttribute("titulo", "Registro de Articulos");
        
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "articulo/formularioArticulo";
    }

    @PostMapping("/guardar-articulo")
    public String guardarArticulo(@Valid Articulo articulo, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status, @RequestParam("file") MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registro de producto");
            return "articulo/formularioArticulo";
        }

        try {
        	if (!file.isEmpty()) {
                String rutaBase = "/static/images/productos/prod-";
                String nombreArchivo = file.getOriginalFilename();
                String rutaCompleta = System.getProperty("user.dir") + "/src/main/resources/static/images/productos/prod-" + nombreArchivo;

                Path rutaImagen = Paths.get(rutaCompleta);
                Files.write(rutaImagen, file.getBytes());

                articulo.setImagen(rutaBase + nombreArchivo);
            }
            articuloService.save(articulo);
            status.setComplete();
            flash.addFlashAttribute("success", "¡Producto registrado con éxito!");
            return "redirect:/articulos";
        } catch (DataIntegrityViolationException e) {
            flash.addFlashAttribute("error", "Error al procesar la imagen o guardar el producto");
            return "redirect:/articulos";
        }
    }

    @GetMapping("/editar-articulo/{id}")
    public String editarArticulo(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        if (id != null && id > 0) {
            Articulo articulo = articuloService.findById(id);
            if (articulo == null) {
                flash.addFlashAttribute("error", "El producto no existe en la base de datos");
                return "redirect:/articulos";
            }
            model.addAttribute("articulo", articulo);
        } else {
            flash.addFlashAttribute("error", "El ID del producto no puede ser nulo o cero");
            return "redirect:/articulos";
        }
        
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Edición de articulo");
        return "articulo/formularioArticulo";
    }

    @GetMapping("/eliminar-articulo/{id}")
    public String eliminarArticulo(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            articuloService.delete(id);
            flash.addFlashAttribute("success", "Articulo eliminado con éxito");
        }
        return "redirect:/articulos";
    }
	
}
