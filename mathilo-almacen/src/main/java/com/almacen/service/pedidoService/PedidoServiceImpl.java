package com.almacen.service.pedidoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.almacen.entitty.Pedido;
import com.almacen.repository.IPedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	@Autowired
	private IPedidoRepository pedidoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pedido> findAll(Pageable pageable) {
		return pedidoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findById(Long id) {
	    Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
	    return optionalPedido.orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pedidoRepository.deleteById(id);
	}

}
