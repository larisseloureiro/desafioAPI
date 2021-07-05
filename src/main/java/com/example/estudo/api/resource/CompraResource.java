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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudo.api.event.RecursoCriadoEvent;
import com.example.estudo.api.exceptionhandler.AccessDeniedException;
import com.example.estudo.api.model.Compra;
import com.example.estudo.api.model.Estoque;
import com.example.estudo.api.model.ItensCompra;
import com.example.estudo.api.repository.CompraRepository;
import com.example.estudo.api.repository.EstoqueRepository;

@RestController
@RequestMapping("compra")
public class CompraResource {

	@Autowired
	private CompraRepository compraRepository;
	@Autowired
	private EstoqueRepository estoqueRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public List<Compra> listar() {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			return compraRepository.findAll();
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
			Optional<Compra> compra = compraRepository.findById(codigo);

			return !compra.isEmpty() ? ResponseEntity.ok(compra)
					: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compra não encontrada!");
		}
		throw new AccessDeniedException();

	}

	// salvar nova compra
	@PostMapping
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<Compra> novaCompra(@Validated @RequestBody Compra compra, HttpServletResponse response) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Compra compraSalva = compraRepository.save(compra);

			List<ItensCompra> itensCompra = compra.getItensCompra();

			for (ItensCompra itemCompra : itensCompra) {
				boolean exist = estoqueRepository.existsByProduto_Codigo(itemCompra.getProduto().getCodigo());

				if (exist == true) {
					Estoque estoque = estoqueRepository.findByProduto_Codigo(itemCompra.getProduto().getCodigo());

					estoque.setQuantidade(estoque.getQuantidade() + itemCompra.getQuantidade());
					estoque.setValorVenda(itemCompra.getValor());
					estoqueRepository.save(estoque);

				}
			}

			publisher.publishEvent(new RecursoCriadoEvent(this, response, compraSalva.getCodigo()));

			return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
		}
		throw new AccessDeniedException();

	}
}
