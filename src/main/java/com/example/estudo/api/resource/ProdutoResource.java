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
import com.example.estudo.api.model.Produto;
import com.example.estudo.api.repository.ProdutoRepository;
import com.example.estudo.api.repository.filter.ProdutoFilter;
import com.example.estudo.api.service.ProdutoService;

@RestController
@RequestMapping("produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PreAuthorize("hasAuthority('Loja')")
	@GetMapping
	public List<Produto> listarAll(ProdutoFilter produtoFilter) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			return produtoRepository.filtrar(produtoFilter);
		}
		throw new AccessDeniedException();
	}
	
	
	//Listar por nome
	@PreAuthorize("hasAuthority('Loja')")
	@GetMapping("/nome/{nome}")
	public List<Produto> listar(ProdutoFilter produtoFilter) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			return produtoRepository.filtrar(produtoFilter);
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
			Optional<Produto> produto = produtoRepository.findById(codigo);
			return !produto.isEmpty() ? ResponseEntity.ok(produto)
					: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
		}
		throw new AccessDeniedException();

	}

	// salvar novo produto
	@PostMapping
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<Produto> novoProduto(@Validated @RequestBody Produto produto, HttpServletResponse response) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Produto produtoSalva = produtoRepository.save(produto);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalva.getCodigo()));

			return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalva);
		}
		throw new AccessDeniedException();

	}

	// atualizar produto
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('Loja')")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigo, @Validated @RequestBody Produto produto) {

		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			Produto produtoSalva = produtoService.atualizar(codigo, produto);
			return ResponseEntity.ok(produtoSalva);
		}
		throw new AccessDeniedException();

	}

	// deletar produto por codigo
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('Loja')")
	public void remover(@PathVariable Long codigo) {
		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		if (perfis.stream().anyMatch(p -> p.getAuthority().equals("Loja"))) {
			produtoRepository.deleteById(codigo);
		}
		
	}

}
