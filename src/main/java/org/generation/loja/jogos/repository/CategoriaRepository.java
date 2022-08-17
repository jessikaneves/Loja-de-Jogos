package org.generation.loja.jogos.repository;

import java.util.List;

import org.generation.loja.jogos.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
	public List<CategoriaModel> findAllByClasseContainingIgnoreCase ( String categoria);
}

