package br.com.ritcher.sistema.seguranca.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.data.Usuario;

public interface UsuarioRepository 
					extends ReactiveCrudRepository<Usuario, Long>, ReactiveSortingRepository<Usuario, Long>{
	Flux<Usuario> findByUsernameContainsAllIgnoringCaseOrderByUsername(String query, Pageable pages);

}
