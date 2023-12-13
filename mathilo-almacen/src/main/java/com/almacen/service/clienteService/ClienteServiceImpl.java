package com.almacen.service.clienteService;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.almacen.entitty.Cliente;
import com.almacen.repository.IClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private IClienteRepository clienteRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
	    Optional<Cliente> optionalCliente = clienteRepository.findById(id);
	    return optionalCliente.orElse(null);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

}
