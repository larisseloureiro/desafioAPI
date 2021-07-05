package com.example.estudo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.estudo.api.model.Estoque;
import com.example.estudo.api.repository.EstoqueRepository;

@Service
public class EstoqueService {
	@Autowired
	private EstoqueRepository estoqueRepository;

	// atualizar estoque
	public Estoque atualizar(Long codigo, Estoque estoque) {
		Optional<Estoque> estoqueSalva = buscarEstoquePeloCodigo(codigo);

		// ta copiando de Estoque para estoqueSalva ignorando o codigo
		BeanUtils.copyProperties(estoque, estoqueSalva.get(), "codigo");
		return estoqueRepository.save(estoqueSalva.get());
	}
	
	//nao duplicar
	private Optional<Estoque> buscarEstoquePeloCodigo(Long codigo) {
		Optional<Estoque> estoqueSalva = estoqueRepository.findById(codigo);

		if (estoqueSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return estoqueSalva;
	}
}
