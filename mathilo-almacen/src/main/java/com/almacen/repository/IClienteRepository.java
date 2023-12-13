package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{

    
}
