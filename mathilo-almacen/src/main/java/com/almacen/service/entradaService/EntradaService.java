package com.almacen.service.entradaService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.almacen.entitty.Entrada;

public interface EntradaService {
	
	public List<Entrada> findAll();

	public Page<Entrada> findAll(Pageable pageable);

	public void save(Entrada entrada);

	public Entrada findById(Long id);

	public void delete(Long id);

}
