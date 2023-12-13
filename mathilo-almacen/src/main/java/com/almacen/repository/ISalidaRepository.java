package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Salida;

@Repository
public interface ISalidaRepository extends JpaRepository<Salida, Long> {

}


