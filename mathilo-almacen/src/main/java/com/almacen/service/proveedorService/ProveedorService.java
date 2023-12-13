package com.almacen.service.proveedorService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.almacen.entitty.Proveedor;
import java.util.List;

public interface ProveedorService {

	public List<Proveedor> findAll();

	public Page<Proveedor> findAll(Pageable pageable);

	public void save(Proveedor proveedor);

	public Proveedor findById(Long id);

	public void delete(Long id);

}
