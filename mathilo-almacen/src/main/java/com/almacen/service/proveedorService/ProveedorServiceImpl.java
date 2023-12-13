package com.almacen.service.proveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.entitty.Proveedor;
import com.almacen.repository.IProveedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	@Autowired
	private IProveedorRepository proveedorRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> findAll() {
		return proveedorRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Proveedor> findAll(Pageable pageable) {
		return proveedorRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor findById(Long id) {
	    Optional<Proveedor> optionalProveedor = proveedorRepository.findById(id);
	    return optionalProveedor.orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		proveedorRepository.deleteById(id);
	}

}
