package com.example.estudo.api.repository.produto;

import java.util.List;

import com.example.estudo.api.model.Produto;
import com.example.estudo.api.repository.filter.ProdutoFilter;

public interface ProdutoRepositoryQuery {
	public List<Produto> filtrar(ProdutoFilter produtoFilter);
	
	
}
