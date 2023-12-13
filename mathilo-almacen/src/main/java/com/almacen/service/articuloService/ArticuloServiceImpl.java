package com.almacen.service.articuloService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.entitty.Articulo;
import com.almacen.repository.IArticuloRepository;


@Service
public class ArticuloServiceImpl implements ArticuloService {
	
	@Autowired
	private IArticuloRepository articuloRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findAll() {
		return articuloRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Articulo> findAll(Pageable pageable) {
		return articuloRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Articulo articulo) {
		articuloRepository.save(articulo);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Articulo findById(Long id) {
	    Optional<Articulo> optionalArticulo = articuloRepository.findById(id);
	    return optionalArticulo.orElse(null);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		articuloRepository.deleteById(id);
		
	}
	
}

