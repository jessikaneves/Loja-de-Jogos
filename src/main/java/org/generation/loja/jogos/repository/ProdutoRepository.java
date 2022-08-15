package org.generation.loja.jogos.repository;

import java.math.BigDecimal;
import java.util.List;

import org.generation.loja.jogos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



public interface ProdutoRepository extends JpaRepository <ProdutoModel, Long>{
	public List<ProdutoModel> findAllByPrecoBetween(@Param("inicio") BigDecimal inicio, @Param("fim") BigDecimal fim);

	public List<ProdutoModel> findAllByItemContainingIgnoreCase(@Param("item") String item);
}