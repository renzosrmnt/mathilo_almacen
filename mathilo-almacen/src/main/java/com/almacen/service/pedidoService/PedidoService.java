package com.almacen.service.pedidoService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.almacen.entitty.Pedido;

public interface PedidoService {
	
	public List<Pedido> findAll();

	public Page<Pedido> findAll(Pageable pageable);

	public void save(Pedido pedido);

	public Pedido findById(Long id);

	public void delete(Long id);

}
