package com.example.estudo.api.resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudo.api.event.RecursoCriadoEvent;
import com.example.estudo.api.exceptionhandler.AccessDeniedException;
import com.example.estudo.api.model.Fornecedor;
import com.example.estudo.api.repository.FornecedorRepository;
import com.example.estudo.api.service.FornecedorService;

@RestController
@RequestMapping("fornecedor")
public class FornecedorResource {
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('Loja')")
	public List<Fornecedor> listar() {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			return fornecedorRepository.findAll();
		}
		throw new AccessDeniedException();

	}

	// buscando pelo cod e msg de erro
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Optional<Fornecedor> fornecedor = fornecedorRepository.findById(codigo);

			return !fornecedor.isEmpty() ? ResponseEntity.ok(fornecedor)
					: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado!");
		}
		throw new AccessDeniedException();

	}

	// salvar novo fornecedor
	@PostMapping
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<Fornecedor> novoFornecedor(@Validated @RequestBody Fornecedor fornecedor,
			HttpServletResponse response) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Fornecedor fornecedorSalva = fornecedorRepository.save(fornecedor);

			publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalva.getCodigo()));

			return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalva);
		}
		throw new AccessDeniedException();

	}

	// atualizar fornecedor
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long codigo,
			@Validated @RequestBody Fornecedor fornecedor) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Fornecedor fornecedorSalva = fornecedorService.atualizar(codigo, fornecedor);
			return ResponseEntity.ok(fornecedorSalva);
		}
		throw new AccessDeniedException();

	}

	// deletar fornecedor por codigo
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('Loja')")
	public void remover(@PathVariable Long codigo) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			fornecedorRepository.deleteById(codigo);
		}
		throw new AccessDeniedException();

	}

}
