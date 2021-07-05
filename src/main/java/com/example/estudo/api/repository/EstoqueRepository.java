package com.example.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudo.api.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

	boolean existsByProduto_Codigo(Long codigo);
	
	Estoque findByProduto_Codigo(Long codigo);

}
