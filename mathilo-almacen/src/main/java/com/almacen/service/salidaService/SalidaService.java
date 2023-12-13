package com.almacen.service.salidaService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.almacen.entitty.Salida;

public interface SalidaService {
	
	public List<Salida> findAll();

	public Page<Salida> findAll(Pageable pageable);

	public void save(Salida salida);

	public Salida findById(Long id);

	public void delete(Long id);

}
