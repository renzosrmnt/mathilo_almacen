package com.almacen.service.entradaService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.almacen.entitty.Entrada;
import com.almacen.repository.IEntradaRepository;

@Service
public class EntradaServiceImpl implements EntradaService {
	
	@Autowired
	private IEntradaRepository entradaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Entrada> findAll() {
		return entradaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Entrada> findAll(Pageable pageable) {
		return entradaRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Entrada entrada) {
		entradaRepository.save(entrada);
	}

	@Override
	@Transactional(readOnly = true)
	public Entrada findById(Long id) {
	    Optional<Entrada> optionalEntrada = entradaRepository.findById(id);
	    return optionalEntrada.orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		entradaRepository.deleteById(id);
	}

}
