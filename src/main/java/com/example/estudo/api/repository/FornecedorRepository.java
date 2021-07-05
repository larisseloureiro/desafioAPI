package com.example.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudo.api.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
