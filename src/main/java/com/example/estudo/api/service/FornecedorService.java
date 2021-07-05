package com.example.estudo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.estudo.api.model.Fornecedor;
import com.example.estudo.api.repository.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	private FornecedorRepository fornecedorRepository;

	// atualizar fornecedor
	public Fornecedor atualizar(Long codigo, Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorSalva = buscarFornecedorPeloCodigo(codigo);

		// ta copiando de Fornecedor para FornecedorSalva ignorando o codigo
		BeanUtils.copyProperties(fornecedor, fornecedorSalva.get(), "codigo");
		return fornecedorRepository.save(fornecedorSalva.get());
	}
	
	//nao duplicar
	private Optional<Fornecedor> buscarFornecedorPeloCodigo(Long codigo) {
		Optional<Fornecedor> fornecedorSalva = fornecedorRepository.findById(codigo);

		if (fornecedorSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedorSalva;
	}
}
