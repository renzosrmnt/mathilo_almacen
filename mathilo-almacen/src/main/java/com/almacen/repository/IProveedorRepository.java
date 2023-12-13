package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Proveedor;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
	
}
