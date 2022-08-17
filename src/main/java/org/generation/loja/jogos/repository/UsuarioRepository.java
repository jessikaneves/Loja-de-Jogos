package org.generation.loja.jogos.repository;


import java.util.Optional;

import org.generation.loja.jogos.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

	@Repository
	public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

		public Optional<UsuarioModel> findByUsuario(String usuario);
		
	}