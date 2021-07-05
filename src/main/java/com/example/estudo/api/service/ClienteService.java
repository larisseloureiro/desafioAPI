package com.example.estudo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.estudo.api.model.Cliente;
import com.example.estudo.api.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	// atualizar cliente
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Optional<Cliente> clienteSalva = buscarClientePeloCodigo(codigo);

		// ta copiando de Cliente para clienteSalva ignorando o codigo
		BeanUtils.copyProperties(cliente, clienteSalva.get(), "codigo");
		return clienteRepository.save(clienteSalva.get());
	}
	
	//nao duplicar
	private Optional<Cliente> buscarClientePeloCodigo(Long codigo) {
		Optional<Cliente> clienteSalva = clienteRepository.findById(codigo);

		if (clienteSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteSalva;
	}
}
