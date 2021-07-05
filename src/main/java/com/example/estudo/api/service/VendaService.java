package com.example.estudo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.estudo.api.model.Venda;
import com.example.estudo.api.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	// atualizar venda
	public Venda atualizar(Long codigo, Venda venda) {
		Optional<Venda> vendaSalva = buscarVendaPeloCodigo(codigo);

		// ta copiando de Venda para VendaSalva ignorando o codigo
		BeanUtils.copyProperties(venda, vendaSalva.get(), "codigo");

		return vendaRepository.save(vendaSalva.get());
	}

	// nao duplicar
	private Optional<Venda> buscarVendaPeloCodigo(Long codigo) {
		Optional<Venda> vendaSalva = vendaRepository.findById(codigo);

		if (vendaSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaSalva;
	}
}
