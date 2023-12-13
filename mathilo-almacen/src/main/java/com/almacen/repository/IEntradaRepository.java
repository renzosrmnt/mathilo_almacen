package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Entrada;

@Repository
public interface IEntradaRepository extends JpaRepository<Entrada, Long> {

}
