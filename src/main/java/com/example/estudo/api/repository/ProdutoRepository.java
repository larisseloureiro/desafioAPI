package com.example.estudo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudo.api.model.Produto;
import com.example.estudo.api.repository.produto.ProdutoRepositoryQuery;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {


	 
}
