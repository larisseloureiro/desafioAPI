package com.example.estudo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.estudo.api.model.Produto;
import com.example.estudo.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	// atualizar produto
	public Produto atualizar(Long codigo, Produto produto) {
		Optional<Produto> produtoSalva = buscarProdutoPeloCodigo(codigo);

		// ta copiando de Produto para produtoSalva ignorando o codigo
		BeanUtils.copyProperties(produto, produtoSalva.get(), "codigo");
		return produtoRepository.save(produtoSalva.get());
	}
	
	//nao duplicar
	private Optional<Produto> buscarProdutoPeloCodigo(Long codigo) {
		Optional<Produto> produtoSalva = produtoRepository.findById(codigo);

		if (produtoSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoSalva;
	}
}
