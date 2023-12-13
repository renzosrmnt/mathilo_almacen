package com.almacen.service.salidaService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.almacen.entitty.Salida;
import com.almacen.repository.ISalidaRepository;

@Service
public class SalidaServiceImpl implements SalidaService {

	@Autowired
	private ISalidaRepository salidaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Salida> findAll() {
		return salidaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Salida> findAll(Pageable pageable) {
		return salidaRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Salida salida) {
		salidaRepository.save(salida);
	}

	@Override
	@Transactional(readOnly = true)
	public Salida findById(Long id) {
	    Optional<Salida> optionalSalida= salidaRepository.findById(id);
	    return optionalSalida.orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		salidaRepository.deleteById(id);
	}

}
