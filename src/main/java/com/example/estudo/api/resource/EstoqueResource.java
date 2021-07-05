package com.example.estudo.api.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudo.api.exceptionhandler.AccessDeniedException;
import com.example.estudo.api.model.Estoque;
import com.example.estudo.api.repository.EstoqueRepository;
import com.example.estudo.api.service.EstoqueService;

@RestController
@RequestMapping("estoque")
public class EstoqueResource {
	
	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private EstoqueService estoqueService;

	@GetMapping
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public List<Estoque> listar() {
		
		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		
		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			return estoqueRepository.findAll();
		}
		
		
		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			List<Estoque> estoque =  estoqueRepository.findAll();
			
			List<Estoque> listEstoque = new ArrayList<>();
			for(Estoque itemEstoque : estoque) {
				if(itemEstoque.getQuantidade() > 0 && itemEstoque.getValorVenda() > 0.0) {
					listEstoque.add(itemEstoque);
				}
			}
			
			return listEstoque;
			
		}
		
		
		throw new AccessDeniedException();
		
	}
	
	// buscando pelo cod e msg de erro
		@GetMapping("/{codigo}")
		@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
		public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
			Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
					.getAuthorities();

			if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja")) || perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
				Optional<Estoque> estoque = estoqueRepository.findById(codigo);

				return !estoque.isEmpty() ? ResponseEntity.ok(estoque) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto no estoque não encontrado!");
			}
			throw new AccessDeniedException();	
			
		}
	
	// atualizar estoque
		@PutMapping("/{codigo}")
		@PreAuthorize("hasAuthority('Loja')")
		public ResponseEntity<Estoque> atualizar(@PathVariable Long codigo,
				@Validated @RequestBody Estoque estoque) {
			Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
					.getAuthorities();

			if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
				Estoque estoqueSalva = estoqueService.atualizar(codigo, estoque);
				return ResponseEntity.ok(estoqueSalva);
			}
			throw new AccessDeniedException();
			
			
		}
}
