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
import com.example.estudo.api.model.Cliente;
import com.example.estudo.api.repository.ClienteRepository;
import com.example.estudo.api.service.ClienteService;

@RestController
@RequestMapping("cliente")
public class ClienteResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	// buscando pelo cod e msg de erro
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Optional<Cliente> cliente = clienteRepository.findById(codigo);

			return !cliente.isEmpty() ? ResponseEntity.ok(cliente)
					: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		throw new AccessDeniedException();

	}

	// salvar novo cliente
	@PostMapping
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public ResponseEntity<Cliente> novoCliente(@Validated @RequestBody Cliente cliente, HttpServletResponse response) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			Cliente clienteSalva = clienteRepository.save(cliente);

			publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalva.getCodigo()));

			return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalva);
		}
		throw new AccessDeniedException();
	}

	// atualizar cliente
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long codigo, @Validated @RequestBody Cliente cliente) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			Cliente clienteSalva = clienteService.atualizar(codigo, cliente);
			return ResponseEntity.ok(clienteSalva);
		}
		throw new AccessDeniedException();

	}

	// deletar cliente por codigo
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			clienteRepository.deleteById(codigo);
		}
		throw new AccessDeniedException();

	}

}
