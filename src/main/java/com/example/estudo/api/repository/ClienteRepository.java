package com.example.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudo.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
