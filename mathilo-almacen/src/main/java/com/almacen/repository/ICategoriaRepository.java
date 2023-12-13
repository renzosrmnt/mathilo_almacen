package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.entitty.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

}
