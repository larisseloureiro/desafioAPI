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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudo.api.event.RecursoCriadoEvent;
import com.example.estudo.api.exceptionhandler.AccessDeniedException;
import com.example.estudo.api.model.Estoque;
import com.example.estudo.api.model.ItensVenda;
import com.example.estudo.api.model.Venda;
import com.example.estudo.api.repository.EstoqueRepository;
import com.example.estudo.api.repository.VendaRepository;
import com.example.estudo.api.service.VendaService;

@RestController
@RequestMapping("venda")
public class VendaResource {

	private static final ResponseEntity<?> StatusVenda = null;
	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private EstoqueRepository estoqueRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private VendaService vendaService;

	private StatusVenda statusVenda;

	@GetMapping
	@PreAuthorize("hasAuthority('Loja')")
	public List<Venda> listar() {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			return vendaRepository.findAll();
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
			Optional<Venda> venda = vendaRepository.findById(codigo);

			return !venda.isEmpty() ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
		}
		throw new AccessDeniedException();

	}

	// salvar nova venda
	@PostMapping
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public ResponseEntity<?> novaVenda(@Validated @RequestBody Venda venda, HttpServletResponse response) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {

			venda.setStatusVenda(statusVenda.PENDENTE);
			Venda vendaSalva = vendaRepository.save(venda);

			List<ItensVenda> itensVenda = venda.getItensVenda();

			for (ItensVenda itemVenda : itensVenda) {
				boolean exist = estoqueRepository.existsByProduto_Codigo(itemVenda.getProduto().getCodigo());

				if (exist == true) {
					Estoque estoque = estoqueRepository.findByProduto_Codigo(itemVenda.getProduto().getCodigo());

					if (estoque.getQuantidade() < itemVenda.getQuantidade()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade insuficiente!");

					}
					// atualizar a quantidade do estoque pós venda
					estoque.setQuantidade(estoque.getQuantidade() - itemVenda.getQuantidade());

					// atualizar valor de venda
					itemVenda.setValor(
							itemVenda.getQuantidade() * (estoque.getValorVenda() * 0.30f + estoque.getValorVenda()));

					estoqueRepository.save(estoque);
				}
			}
			publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getCodigo()));

			return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
		}
		throw new AccessDeniedException();
	}

	// atualizar venda
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja') or hasAuthority('Cliente')")
	public ResponseEntity<Venda> atualizar(@PathVariable Long codigo, @Validated @RequestBody Venda venda) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))
				|| perfis.stream().anyMatch(p -> p.getAuthority().equals("Cliente"))) {
			venda.setStatusVenda(statusVenda.RECEBIDO);
			Venda vendaSalva = vendaService.atualizar(codigo, venda);
			return ResponseEntity.ok(vendaSalva);
		}
		throw new AccessDeniedException();

	}

}
