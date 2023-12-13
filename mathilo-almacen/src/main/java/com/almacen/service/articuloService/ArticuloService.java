package com.almacen.service.articuloService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.almacen.entitty.Articulo;

public interface ArticuloService {

	
	public List<Articulo> findAll();

	public Page<Articulo> findAll(Pageable pageable);

	public void save(Articulo articulo);

	public Articulo findById(Long id);

	public void delete(Long id);

}
