package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Articulo;

@Repository
public interface IArticuloRepository extends JpaRepository<Articulo, Long>{

}
