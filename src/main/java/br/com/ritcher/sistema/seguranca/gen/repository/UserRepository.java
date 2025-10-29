package br.com.ritcher.sistema.seguranca.gen.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.gen.data.User;

public interface UserRepository 
					extends ReactiveCrudRepository<User, Long>, ReactiveSortingRepository<User, Long>{
    Flux<User> findAllBy(Pageable pages);
    Flux<User> findByUsernameContainsAllIgnoringCaseOrderByUsername(String query, Pageable pages);

}
