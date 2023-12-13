package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

}
