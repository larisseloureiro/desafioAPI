package com.example.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudo.api.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
